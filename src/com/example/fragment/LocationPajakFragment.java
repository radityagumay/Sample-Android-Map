package com.example.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.pajak.R;
import com.example.pajak.adapter.LocationPajakFragmentAdapter;
import com.example.pajak.model.LocationModel;
import com.example.utils.AppConstant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class LocationPajakFragment extends Fragment implements
		com.example.utils.EventMap.OnMapListener {

	public static final String ARG_SECTION_NUMBER = "section_number";

	private static final String URL = AppConstant.URL;

	private static final LatLng LOCATION_ME = new LatLng(-8.673099, 115.226651);

	private ListView mListView;
	private LocationPajakFragmentAdapter mAdapter;

	private GoogleMap map;
	private Activity mActivity;
	private MapView mapView;

	private View mView;

	private int resultCode;

	private List<LocationModel> mList;

	private LocationPajakFragment() {
	}

	public static LocationPajakFragment newInstance(int i) {
		LocationPajakFragment fragment = new LocationPajakFragment();
		Bundle args = new Bundle();
		args.putInt(LocationPajakFragment.ARG_SECTION_NUMBER, i);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mList = new ArrayList<LocationModel>();
		resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(mActivity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_location_pajak, container,
				false);
		mapView = (MapView) mView.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		if (mapView != null) {
			map = mapView.getMap();
			map.setMyLocationEnabled(true);
			map.getUiSettings().setMyLocationButtonEnabled(true);
			Marker stikom = map.addMarker(new MarkerOptions().position(
					LOCATION_ME).title("My Location"));
		}
		return mView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// new HttpGetAsyncTask().execute();
		List<LocationModel> list = getListLocation();

		setAdapter(list);
		InitializeMap(list);
	}

	private void setAdapter(List<LocationModel> list) {
		mAdapter = new LocationPajakFragmentAdapter(mActivity, list, this);
		mListView = (ListView) mView.findViewById(R.id.lv_list);
		mListView.setAdapter(mAdapter);
		InitializeMap(list);
	}

	private void InitializeMap(List<LocationModel> mList) {
		MapsInitializer.initialize(mActivity);
		switch (resultCode) {
		case ConnectionResult.SUCCESS:
			if (mapView != null) {
				for (int i = 0; i < mList.size(); i++) {
					LatLng point = new LatLng(mList.get(i).getLatitude(), mList
							.get(i).getLongitude());

					map.addMarker(new MarkerOptions()
							.position(point)
							.title(mList.get(i).getName())
							.snippet("Kantor Pajak")
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.ic_launcher)));
				}
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_ME,
						15));

				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(LOCATION_ME) // Sets the center of the map
												// toMountain View
						.zoom(13) // Sets the zoom
						.bearing(0) // Sets the orientation of the camera teast
						.tilt(30) // Sets the tilt of the camera to 30 degrees
						.build(); // Creates a CameraPosition from the builder
				map.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
			}
			break;
		case ConnectionResult.SERVICE_MISSING:
			Toast.makeText(mActivity, "SERVICE MISSING", Toast.LENGTH_SHORT)
					.show();
			break;
		case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
			Toast.makeText(mActivity, "UPDATE REQUIRED", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			Toast.makeText(mActivity, resultCode, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * If you use APIs
	 * 
	 * @author radityagumay
	 */
	public class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				InputStream inputStream = httpResponse.getEntity().getContent();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				StringBuilder stringBuilder = new StringBuilder();

				String bufferedStrChunk = "";
				while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
					stringBuilder.append(bufferedStrChunk);
				}
				return stringBuilder.toString();

			} catch (ClientProtocolException cpe) {
				cpe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONArray jArray = new JSONArray(result);
				JSONObject jObjs = null;
				List<LocationModel> list = new ArrayList<LocationModel>(
						jArray.length());
				for (int i = 1; i < jArray.length(); i++) {
					jObjs = jArray.getJSONObject(i);

					LocationModel model = new LocationModel();
					model.setId(jObjs.getString("idLokasi"));
					model.setAddress(jObjs.getString("alamat"));
					model.setImage(jObjs.getString("image"));
					model.setLatitude(Double.parseDouble(jObjs
							.getString("latitude")));
					model.setLongitude(Double.parseDouble(jObjs
							.getString("longitude")));
					model.setName(jObjs.getString("name"));
					model.setPhone(jObjs.getString("noTlpn"));
					list.add(model);
				}
				if (list != null && list.size() > 0) {
					setAdapter(list);
				}
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
		}
	}

	private void mapRoute(double latitude, double longitude) {
		String uri = String.format(Locale.ENGLISH,
				"http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",
				LOCATION_ME.latitude, LOCATION_ME.longitude, "Your Place",
				latitude, longitude, "Destination");
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		intent.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mapView != null) {
			mapView.onResume();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mapView != null) {
			mapView.onDestroy();
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	private SupportMapFragment getMapFragment() {
		FragmentManager fm = null;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			fm = getFragmentManager();
		} else {
			fm = getChildFragmentManager();
		}
		return (SupportMapFragment) fm.findFragmentById(R.id.map);
	}

	@Override
	public void onClick(double latitude, double longitude) {
		mapRoute(latitude, longitude);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	/**
	 * Unit Test
	 */
	private List<LocationModel> getListLocation() {
		for (int i = 0; i < mActivity.getResources().getStringArray(
				R.array.location_name).length; i++) {
			LocationModel model = new LocationModel();
			model.setName((mActivity.getResources()
					.getStringArray(R.array.location_name))[i]);
			model.setPhone((mActivity.getResources()
					.getStringArray(R.array.location_phone))[i]);
			model.setImage((mActivity.getResources()
					.getStringArray(R.array.location_image))[i]);
			model.setLatitude(Double.parseDouble((mActivity.getResources()
					.getStringArray(R.array.location_latitude))[i]));
			model.setLongitude(Double.parseDouble((mActivity.getResources()
					.getStringArray(R.array.location_longitude))[i]));
			mList.add(model);
		}

		return mList;
	}

}
