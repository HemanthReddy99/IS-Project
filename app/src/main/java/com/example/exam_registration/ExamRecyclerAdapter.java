package com.example.exam_registration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExamRecyclerAdapter extends RecyclerView.Adapter<ExamRecyclerAdapter.ExamViewHolder> {

    private ArrayList<Exam> examArrayList;
    private Context mContext;
    private ArrayList<Exam> mFilteredList;
    private OnExamClickListener monExamClickListener;

    public ExamRecyclerAdapter(ArrayList<Exam> exams, Context mContext, OnExamClickListener onExamClickListener)
    {
        this.examArrayList = exams;
        this.mContext = mContext;
        this.mFilteredList = exams;
        this.monExamClickListener = onExamClickListener;
    }


    public class ExamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView ename;
        public TextView edate;
        public TextView eduration;
        OnExamClickListener onExamClickListener;


        public ExamViewHolder(View view, OnExamClickListener onExamClickListener)
        {
            super(view);
            ename = view.findViewById(R.id.item_exam_name);
            edate = view.findViewById(R.id.item_exam_date);
            eduration = view.findViewById(R.id.item_exam_duration);
            this.onExamClickListener = onExamClickListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onExamClickListener.OnExamClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_exam_list_layout,parent,false);


        return  new ExamViewHolder(itemView,monExamClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {

        holder.ename.setText(examArrayList.get(position).getEname());
        holder.edate.setText(examArrayList.get(position).getEdate());
        holder.eduration.setText(examArrayList.get(position).getEduration());


    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    public interface OnExamClickListener
    {
        void OnExamClick(int position);
    }


}
