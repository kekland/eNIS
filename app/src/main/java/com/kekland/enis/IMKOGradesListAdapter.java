package com.kekland.enis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kekland.enis.NIS.Subject.IMKOLesson;

import java.util.ArrayList;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class IMKOGradesListAdapter extends RecyclerView.Adapter<IMKOGradesListAdapter.IMKOViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<IMKOLesson> mDataSource;

    public IMKOGradesListAdapter(Context context, ArrayList<IMKOLesson> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class IMKOViewHolder extends RecyclerView.ViewHolder {
        TextView SubjectName;
        TextView FormativeText;
        TextView SummativeText;

        ProgressBar FormativeProgress;
        ProgressBar SummativeProgress;
        TextView GradeText;

        TextView DateOfChangeLabel;
        TextView DateOfChangeText;

        public IMKOViewHolder(View rowView) {
            super(rowView);

            SubjectName = (TextView)rowView.findViewById(R.id.layoutImkoSubjectName);
            FormativeText = (TextView)rowView.findViewById(R.id.layoutImkoSubjectFormative);
            SummativeText = (TextView)rowView.findViewById(R.id.layoutImkoSubjectSummative);
            FormativeProgress = (ProgressBar)rowView.findViewById(R.id.layoutImkoSubjectFormativeProgress);
            SummativeProgress = (ProgressBar)rowView.findViewById(R.id.layoutImkoSubjectSummativeProgress);
            GradeText = (TextView)rowView.findViewById(R.id.layoutImkoSubjectGrade);
            DateOfChangeLabel = (TextView)rowView.findViewById(R.id.layoutImkoSubjectDateOfChangeLabel);
            DateOfChangeText = (TextView)rowView.findViewById(R.id.layoutImkoSubjectDateOfChange);
        }
    }
    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override public IMKOViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = mInflater.inflate(R.layout.layout_imko_subject, parent, false);
        return new IMKOViewHolder(rowView);
    }


    @Override public void onBindViewHolder(IMKOViewHolder holder, int position) {

        IMKOLesson les = mDataSource.get(position);


        holder.SubjectName.setText(les.Name);
        holder.FormativeText.setText(les.GetFormativeString());
        holder.SummativeText.setText(les.GetSummativeString());
        holder.GradeText.setText(les.GetGrade());

        if(les.DateOfChange.equals("")) {
            holder.DateOfChangeLabel.setVisibility(View.GONE);
            holder.DateOfChangeText.setVisibility(View.GONE);
        }
        else {
            holder.DateOfChangeLabel.setVisibility(View.VISIBLE);
            holder.DateOfChangeText.setVisibility(View.VISIBLE);
            holder.DateOfChangeText.setText(les.DateOfChange);
        }

        holder.FormativeProgress.setMax(Integer.parseInt(les.MaxFormative));
        holder.SummativeProgress.setMax(Integer.parseInt(les.MaxSummative));

        Integer formativeProgress = 0;
        if(les.Formative != "") {
            formativeProgress = Integer.parseInt(les.Formative);
        }

        Integer summativeProgress = 0;
        if(les.Summative != "") {
            summativeProgress = Integer.parseInt(les.Summative);
        }

        holder.FormativeProgress.setProgress(formativeProgress);
        holder.SummativeProgress.setProgress(summativeProgress);

    }
}
