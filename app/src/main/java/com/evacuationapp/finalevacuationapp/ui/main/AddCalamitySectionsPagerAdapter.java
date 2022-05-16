package com.evacuationapp.finalevacuationapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.evacuationapp.finalevacuationapp.AddPLacesFragmentCalamity;
import com.evacuationapp.finalevacuationapp.ListFragmentCalamity;
import com.evacuationapp.finalevacuationapp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class AddCalamitySectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_4, R.string.tab_text_5};
    private final Context mContext;

    public AddCalamitySectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new AddPLacesFragmentCalamity();
            case 1:return new ListFragmentCalamity();
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
        return 2;
    }
}