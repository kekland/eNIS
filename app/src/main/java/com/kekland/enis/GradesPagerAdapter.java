package com.kekland.enis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kekland.enis.NIS.Subject.SubjectData;

import java.util.ArrayList;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class GradesPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 4;
    GradesListFragment[] fragments = new GradesListFragment[4];

    public GradesPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        for(int i = 0; i < 4; i++) {
            fragments[i] = new GradesListFragment();
        }
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    public void UpdateIMKO(SubjectData data) {
        for(int i = 0; i < 4; i++) {
            fragments[i].UpdateDatasetIMKO(new ArrayList<>(data.GetQuarter(i).GetSubjectsIMKO()));
        }
    }
    public void UpdateJKO(SubjectData data) {
        for(int i = 0; i < 4; i++) {
            fragments[i].UpdateDatasetJKO(new ArrayList<>(data.GetQuarter(i).GetSubjectsJKO()));
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
