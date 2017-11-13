package com.example.dell.mydemostreamred5.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.dell.mydemostreamred5.MainActivity;
import com.example.dell.mydemostreamred5.ui.fragment.PublishFragment;
import com.example.dell.mydemostreamred5.ui.fragment.SubcribesFragment;

import java.util.Locale;

/**
 * Created by DELL on 11/10/2017.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter {
    public SectionPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PublishFragment.newInstance();
            case 1:
                SubcribesFragment.newInstance();
            }
        return MainActivity.PlaceholderFragment.newInstance(position+1);
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l=Locale.getDefault();
        switch (position){
            case 0:
                return "Publish";
            case 1:
                return "Subcribe";
        }
        return null;
    }
}
