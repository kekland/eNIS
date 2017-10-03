package com.kekland.enis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kekland.enis.NIS.NISApi;
import com.kekland.enis.NIS.NISData;
import com.kekland.enis.NIS.NISDiary;
import com.kekland.enis.NIS.Requests.NISApiSubjects;
import com.kekland.enis.NIS.Subject.SubjectData;
import com.kekland.enis.Utilities.MasterListener;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class GradesFragment extends Fragment {

    ViewPager pager;
    GradesPagerAdapter adapter;
    MasterListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sc) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        pager = view.findViewById(R.id.fragmentGradesListPager);
        pager.setOffscreenPageLimit(3);
        adapter = new GradesPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        TabLayout tabs = view.findViewById(R.id.fragmentGradesPeriodTabs);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final FloatingActionButton update = view.findViewById(R.id.fragmentGradesUpdateFAB);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NISData.getDiary() == NISDiary.IMKO) {
                    NISApi.GetJKOSubjects(new NISApiSubjects.JKOSubjectsListener() {
                        @Override
                        public void onStart() {
                            update.hide();
                        }

                        @Override
                        public void onSuccess(SubjectData data) {
                            adapter.UpdateJKO(data);
                            update.show();
                        }

                        @Override
                        public void onFailure(String message) {
                            listener.ShowSnackbar(message);
                            update.show();
                        }
                    });
                }
            }
        });

        return view;
    }

}
