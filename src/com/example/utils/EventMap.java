package com.example.utils;


public class EventMap {
	private OnMapListener mOnEventListener;

    public EventMap(OnMapListener listener) {
        mOnEventListener = listener;
    }
	
    public interface OnMapListener {
        void onClick(double latitude, double longitude);
    }
}
