package com.kekland.enis;

import android.content.Context;
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

public class IMKOGradesListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<IMKOLesson> mDataSource;

    public IMKOGradesListAdapter(Context context, ArrayList<IMKOLesson> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder {
        TextView SubjectName;
        TextView FormativeText;
        TextView SummativeText;

        ProgressBar FormativeProgress;
        ProgressBar SummativeProgress;
        TextView GradeText;

        TextView DateOfChangeLabel;
        TextView DateOfChangeText;

        public ViewHolder(TextView subjectName, TextView formativeText, TextView summativeText, ProgressBar formativeProgress,
                          ProgressBar summativeProgress, TextView gradeText, TextView dateOfChangeLabel, TextView dateOfChangeText) {
            SubjectName = subjectName;
            FormativeText = formativeText;
            SummativeText = summativeText;
            FormativeProgress = formativeProgress;
            SummativeProgress = summativeProgress;
            GradeText = gradeText;
            DateOfChangeLabel = dateOfChangeLabel;
            DateOfChangeText = dateOfChangeText;
        }
    }
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = convertView;
        if(convertView == null) {
            rowView = mInflater.inflate(R.layout.layout_imko_subject, parent, false);

            ViewHolder holder = new ViewHolder(
                    (TextView)rowView.findViewById(R.id.layoutImkoSubjectName),
                    (TextView)rowView.findViewById(R.id.layoutImkoSubjectFormative),
                    (TextView)rowView.findViewById(R.id.layoutImkoSubjectSummative),
                    (ProgressBar)rowView.findViewById(R.id.layoutImkoSubjectFormativeProgress),
                    (ProgressBar)rowView.findViewById(R.id.layoutImkoSubjectSummativeProgress),
                    (TextView)rowView.findViewById(R.id.layoutImkoSubjectGrade),
                    (TextView)rowView.findViewById(R.id.layoutImkoSubjectDateOfChangeLabel),
                    (TextView)rowView.findViewById(R.id.layoutImkoSubjectDateOfChange)
            );

            rowView.setTag(holder);
        }

        IMKOLesson les = mDataSource.get(position);
        ViewHolder holder = (ViewHolder)rowView.getTag();

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

        return rowView;
    }
}
