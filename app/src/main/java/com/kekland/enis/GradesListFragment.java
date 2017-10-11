package com.kekland.enis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.kekland.enis.NIS.Requests.NISApiSubjects;
import com.kekland.enis.NIS.Subject.IMKOLesson;
import com.kekland.enis.NIS.Subject.JKOLesson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class GradesListFragment extends Fragment {
    RecyclerView list;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sc) {
        View view = inflater.inflate(R.layout.fragment_grades_list, container, false);

        list = view.findViewById(R.id.fragmentGradesListView);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(mLinearLayoutManager);

        return view;
    }

    public void UpdateDatasetIMKO(ArrayList<IMKOLesson> listItems) {
        IMKOGradesListAdapter adapter = new IMKOGradesListAdapter(getContext(), listItems);
        list.swapAdapter(adapter, true);
    }

    public void UpdateDatasetJKO(ArrayList<JKOLesson> listItems) {
        JKOGradesListAdapter adapter = new JKOGradesListAdapter(getContext(), listItems);
        list.swapAdapter(adapter, true);
    }
}
