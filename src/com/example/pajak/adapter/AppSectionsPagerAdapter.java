package com.example.pajak.adapter;

import com.example.fragment.DummySectionFragment;
import com.example.fragment.LocationPajakFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

	public AppSectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			return LocationPajakFragment.newInstance(i + 1);
		/* Put Your Fragment
		 * case 1:
			return fragmentName.newInstance(i + 1);*/
		default:
			return DummySectionFragment.newInstance(i + 1);
		}
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Location";
		default:
			return "Other";
		}
	}
}
