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
import com.kekland.enis.NIS.Subject.JKOLesson;

import java.util.ArrayList;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class JKOGradesListAdapter extends RecyclerView.Adapter<JKOGradesListAdapter.JKOViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<JKOLesson> mDataSource;

    public JKOGradesListAdapter(Context context, ArrayList<JKOLesson> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class JKOViewHolder extends RecyclerView.ViewHolder {
        TextView SubjectName;
        TextView PercentageText;
        ProgressBar PercentageProgress;

        TextView GradeText;

        public JKOViewHolder(View rowView) {
            super(rowView);
            SubjectName = (TextView)rowView.findViewById(R.id.layoutJkoSubjectName);
            PercentageText = (TextView)rowView.findViewById(R.id.layoutJkoSubjectPercentage);
            PercentageProgress = (ProgressBar)rowView.findViewById(R.id.layoutJkoSubjectPercentageProgress);
            GradeText = (TextView)rowView.findViewById(R.id.layoutJkoSubjectGrade);
        }
    }
    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    @Override
    public JKOViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new JKOViewHolder(mInflater.inflate(R.layout.layout_jko_subject, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(JKOViewHolder holder, int i) {
        // Get view for row item

        JKOLesson les = mDataSource.get(i);

        holder.SubjectName.setText(les.Name);
        holder.PercentageText.setText(les.PercentString);
        holder.GradeText.setText(les.Mark);

        holder.PercentageProgress.setProgress(les.Percent);
    }
}
