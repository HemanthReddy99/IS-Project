package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherAddExam extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    int flag =0;
    String name;
    String date;
    String time;
    String duration;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_exam);

        final EditText examdate = findViewById(R.id.HostAnExamDate);
        final EditText examname = findViewById(R.id.HostAnExamName);
        final EditText examtime = findViewById(R.id.HostAnEamTime);
        final EditText examduration = findViewById(R.id.HostAnExamDuration);
        final EditText examid = findViewById(R.id.HostAnEamID);
        Button host = findViewById(R.id.HostButton);



        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getBaseContext(),TeacherProfile.class);
                flag =0;
                name = examname.getText().toString();
                date = examdate.getText().toString();
                time = examtime.getText().toString();
                duration = examduration.getText().toString();
                id = examid.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    examname.setError("Enter Name");
                    flag=1;
                }
                if(TextUtils.isEmpty(date)) {
                    examdate.setError("Enter Date");
                    flag=1;
                }
                if(TextUtils.isEmpty(time))
                {
                    examtime.setError("Enter Time");
                    flag=1;
                }
                if(TextUtils.isEmpty(id)) {
                    examid.setError("Enter ID");
                    flag=1;
                }
                if(TextUtils.isEmpty(duration))
                {
                    examduration.setError("Enter Duration");
                    flag=1;
                }

                if(flag==0){

                    boolean insert = false;
                    Boolean chkeid = db.checkExamId(id);

                    if(chkeid==true)
                    {
                        insert = db.insertExamData(id,name,date,duration);

                        if(insert)
                        {
                            Toast.makeText(getApplicationContext(), "Exam Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Exam did not add, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        examid.setError("Id Already Present, Use another");
                    }

                }


            }
        });


    }
}
