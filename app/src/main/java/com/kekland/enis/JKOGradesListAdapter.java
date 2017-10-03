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
import com.kekland.enis.NIS.Subject.JKOLesson;

import java.util.ArrayList;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class JKOGradesListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<JKOLesson> mDataSource;

    public JKOGradesListAdapter(Context context, ArrayList<JKOLesson> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder {
        TextView SubjectName;
        TextView PercentageText;
        ProgressBar PercentageProgress;

        TextView GradeText;

        public ViewHolder(TextView subjectName, TextView percentageText, ProgressBar percentageProgress, TextView gradeText) {
            SubjectName = subjectName;
            PercentageText = percentageText;
            PercentageProgress = percentageProgress;
            GradeText = gradeText;
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
            rowView = mInflater.inflate(R.layout.layout_jko_subject, parent, false);

            ViewHolder holder = new ViewHolder(
                    (TextView)rowView.findViewById(R.id.layoutJkoSubjectName),
                    (TextView)rowView.findViewById(R.id.layoutJkoSubjectPercentage),
                    (ProgressBar)rowView.findViewById(R.id.layoutJkoSubjectPercentageProgress),
                    (TextView)rowView.findViewById(R.id.layoutJkoSubjectGrade)
            );

            rowView.setTag(holder);
        }

        JKOLesson les = mDataSource.get(position);
        ViewHolder holder = (ViewHolder)rowView.getTag();

        holder.SubjectName.setText(les.Name);
        holder.PercentageText.setText(les.PercentString);
        holder.GradeText.setText(les.Mark);

        holder.PercentageProgress.setProgress(les.Percent);

        return rowView;
    }
}
