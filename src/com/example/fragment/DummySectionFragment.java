package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pajak.R;

public class DummySectionFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	public static DummySectionFragment newInstance(int i){
		DummySectionFragment fragment = new DummySectionFragment();
        Bundle args = new Bundle();
        args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
        fragment.setArguments(args);
        return fragment;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_map, container, false);
        return rootView;
    }
}
