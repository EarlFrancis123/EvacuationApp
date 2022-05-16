package com.evacuationapp.finalevacuationapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.evacuationapp.finalevacuationapp.AddPLacesFragmentCalamity;
import com.evacuationapp.finalevacuationapp.AddPlacesFragment;
import com.evacuationapp.finalevacuationapp.AddPlacesFragmentNearestEvacuation;
import com.evacuationapp.finalevacuationapp.ListFragment;
import com.evacuationapp.finalevacuationapp.ListFragmentCalamity;
import com.evacuationapp.finalevacuationapp.ListFragmentNearestEvacuation;
import com.evacuationapp.finalevacuationapp.MapsFragment;
import com.evacuationapp.finalevacuationapp.MapsFragmentNearestEvacuation;
import com.evacuationapp.finalevacuationapp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class UserNearestSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_8, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;

    public UserNearestSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new AddPlacesFragmentNearestEvacuation();
            case 1:return new ListFragmentNearestEvacuation();
            case 2:return new MapsFragmentNearestEvacuation();
            default: return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}