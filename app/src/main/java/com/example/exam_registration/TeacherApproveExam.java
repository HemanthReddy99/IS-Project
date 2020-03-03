package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherApproveExam extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    String EID;
    String AID;
    String SID;
    String Edate;
    String Ename;
    String Smail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_approve_exam);

        Intent getData = getIntent();
        EID = getData.getStringExtra("EID");
        SID = getData.getStringExtra("SID");
        Edate = getData.getStringExtra("Edate");
        Ename = getData.getStringExtra("Ename");
        Smail = getData.getStringExtra("Smail");

        TextView eid = findViewById(R.id.teacher_approve_exam_id);
        TextView ename = findViewById(R.id.teacher_approve_exam_subject);
        TextView edate = findViewById(R.id.teacher_approve_exam_date);
        TextView smail = findViewById(R.id.teacher_approve_exam_student_mail);

        eid.setText(EID);
        ename.setText(Ename);
        edate.setText(Edate);
        smail.setText(Smail);

        Button approve = findViewById(R.id.teacher_approve_exam_button);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.teacherApproveExam(SID,EID);
                Toast.makeText(getApplicationContext(),"Student Approved!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),TeacherProfile.class);
                startActivity(intent);
            }
        });

        TextView v1 = findViewById(R.id.teacherdeleteexam);

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Exam Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherApproveExam.this,TeacherProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                db.admindeleteexam(EID);
                startActivity(intent);
            }
        });



    }
}
