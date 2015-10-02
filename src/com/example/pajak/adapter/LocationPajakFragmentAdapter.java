package com.example.pajak.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.example.pajak.R;
import com.example.pajak.model.LocationModel;
import com.example.utils.EventMap.OnMapListener;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LocationPajakFragmentAdapter extends BaseAdapter {

	private List<LocationModel> mUserModels;
	private Activity mActivity;
	private OnMapListener mListener;
	
	public LocationPajakFragmentAdapter(Activity activity, List<LocationModel> models, OnMapListener listener){
		this.mActivity = activity;
		this.mUserModels = models;
		this.mListener = listener;
	}
	

	@Override
	public int getCount() {
		return mUserModels.size();	
	}

	@Override
	public Object getItem(int position) {
		return mUserModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		
		if (view == null){
			view = mActivity.getLayoutInflater().inflate(R.layout.fragment_location_pajak_list_item, parent, false);
			holder = new ViewHolder();
			holder.mImageView = (ImageView)view.findViewById(R.id.iv_imageView);
			holder.mName = (TextView)view.findViewById(R.id.tv_name);
			holder.mPhone = (TextView)view.findViewById(R.id.tv_phone);
			holder.mAddress = (TextView)view.findViewById(R.id.tv_address);
			view.setTag(holder);
		}else{
			holder = (ViewHolder)view.getTag();
		}
		
		holder = (ViewHolder)view.getTag();
		
		holder.mName.setText(mUserModels.get(position).getName());
		holder.mPhone.setText(String.valueOf(mUserModels.get(position).getPhone()));
		//holder.mAddress.setText(String.valueOf(mUserModels.get(position).getLongitude()));
		Glide.with(mActivity)
		.load(mUserModels.get(position).getImage())
		.centerCrop()
		.crossFade()
		.into(holder.mImageView);
		
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mListener != null){
					mListener.onClick(mUserModels.get(position).getLatitude(), mUserModels.get(position).getLongitude());
				}
			}
		});
		return view;
	}

	private static class ViewHolder{
		private TextView mName, mPhone, mAddress;
		private ImageView mImageView;
	}
}
