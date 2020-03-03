package com.example.exam_registration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RegisteredExamRecyclerAdapter extends RecyclerView.Adapter<RegisteredExamRecyclerAdapter.RegisteredExamViewHolder> {

    private ArrayList<RegisteredExam> registeredExamArrayList;
    private Context mContext;
    private ArrayList<RegisteredExam> mFilteredList;

    public RegisteredExamRecyclerAdapter(ArrayList<RegisteredExam> exams, Context mContext)
    {
        this.registeredExamArrayList = exams;
        this.mContext = mContext;
        this.mFilteredList = exams;

    }

    public class RegisteredExamViewHolder extends RecyclerView.ViewHolder
    {
        public TextView ename;
        public TextView edate;
        public TextView eduration;
        public TextView status;

        public RegisteredExamViewHolder(View view)
        {
            super(view);
            ename = view.findViewById(R.id.registered_item_exam_name);
            edate = view.findViewById(R.id.registered_item_exam_date);
            eduration = view.findViewById(R.id.registered_item_exam_duration);
            status = view.findViewById(R.id.registered_item_exam_status);



        }

//        @Override
//        public void onClick(View view) {
//            onExamClickListener.OnRegisteredExamClick(getAdapterPosition());
//        }
    }

    @NonNull
    @Override
    public RegisteredExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_registered_exam_list_item,parent,false);


        return  new RegisteredExamRecyclerAdapter.RegisteredExamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredExamViewHolder holder, int position) {

        holder.ename.setText(registeredExamArrayList.get(position).getExamName());
        holder.edate.setText(registeredExamArrayList.get(position).getExamDate());
        holder.eduration.setText(registeredExamArrayList.get(position).getExamDuration());
        holder.status.setText(registeredExamArrayList.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

//    public interface OnRegisteredExamClickListener
//    {
//        void OnRegisteredExamClick(int position);
//    }

}


