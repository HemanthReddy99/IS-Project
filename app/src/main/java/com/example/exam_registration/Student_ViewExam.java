package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Student_ViewExam extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    String Adminname = "";
    String EID;
    String AID;
    String SID;
    boolean insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_exam);

        Intent intent_from_list_item = getIntent();

        EID = intent_from_list_item.getStringExtra("EID");
        AID = intent_from_list_item.getStringExtra("AID");
        SID = db.getsid();
        //Adminname = db.get_admin_name_for_exam(AID);

        TextView subject = findViewById(R.id.student_view_exam_subject);
        if(intent_from_list_item.hasExtra("Ename"))
        {
            subject.setText(intent_from_list_item.getStringExtra("Ename"));
        }

        TextView date = findViewById(R.id.student_exam_view_date);
        if(intent_from_list_item.hasExtra("Edate"))
        {
            date.setText(intent_from_list_item.getStringExtra("Edate"));
        }

        TextView duration = findViewById(R.id.student_exam_view_duration);
        if(intent_from_list_item.hasExtra("Eduration"))
        {
            duration.setText(intent_from_list_item.getStringExtra("Eduration"));
        }

        TextView admin_name = findViewById(R.id.student_exam_view_admin_name);
        admin_name.setText(AID);


        Button student_exam_register = findViewById(R.id.student_exam_view_register);

        student_exam_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insert = db.insert_student_applied(SID,EID,AID);
                int flag = 1;

                if(insert)
                {
                    flag = 0;
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent tpintent = new Intent(getBaseContext(), StudentProfile.class);
                    startActivity(tpintent);
                }
                else
                {   flag = 0;
                    Toast.makeText(getApplicationContext(),"Did Not Register Properly, Try Again",Toast.LENGTH_SHORT);
                }

                if(flag==1) {
                    Toast.makeText(getApplicationContext(), " Already Registered", Toast.LENGTH_SHORT).show();
                    Intent tp1intent = new Intent(getBaseContext(), StudentProfile.class);
                    startActivity(tp1intent);
                }

            }
        });
    }
}
