package com.example.exam_registration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherExamRecyclerAdapter extends RecyclerView.Adapter<TeacherExamRecyclerAdapter.TeacherExamViewHolder> {

    private ArrayList<TeacherExam> examArrayList;
    private Context mContext;
    private ArrayList<TeacherExam> mFilteredList;
    private OnTeacherExamClickListener monExamClickListener;

    public TeacherExamRecyclerAdapter(ArrayList<TeacherExam> examArrayList, Context mContext, OnTeacherExamClickListener monExamClickListener) {
        this.examArrayList = examArrayList;
        this.mContext = mContext;
        this.mFilteredList = examArrayList;
        this.monExamClickListener = monExamClickListener;
    }

    public class TeacherExamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView ename;
        public TextView edate;
        public TextView eid;
        OnTeacherExamClickListener onExamClickListener;

        public TeacherExamViewHolder(View view, OnTeacherExamClickListener onExamClickListener)
        {
            super(view);
            ename = view.findViewById(R.id.teacher_item_exam_name);
            edate = view.findViewById(R.id.teacher_item_exam_date);
            eid = view.findViewById(R.id.teacher_item_exam_id);
            this.onExamClickListener = onExamClickListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onExamClickListener.OnTeacherExamClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public TeacherExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_exam_list_layout,parent,false);


        return  new TeacherExamViewHolder(itemView,monExamClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherExamViewHolder holder, int position) {

        holder.ename.setText(examArrayList.get(position).getEname());
        holder.edate.setText(examArrayList.get(position).getEdate());
        holder.eid.setText(examArrayList.get(position).getEid());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    public interface OnTeacherExamClickListener
    {
        void OnTeacherExamClick(int position);
    }


}
