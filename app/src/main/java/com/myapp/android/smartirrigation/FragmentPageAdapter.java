package com.myapp.android.smartirrigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentPageAdapter extends FragmentStatePagerAdapter
{
    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        PageFragment pageFragment=new PageFragment();
        Bundle bundle=new Bundle();
        ++position;
        bundle.putString("message","Page "+position);
        bundle.putInt("position",position);
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
