package com.example.exam_registration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StudentProfile extends AppCompatActivity {
    TextView v1,v2,v3,v4;
    DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        v1 = findViewById(R.id.profilename);
        v2 = findViewById(R.id.profileemail);
        v3 = findViewById(R.id.profilecgpa);
        v4 = findViewById(R.id.studentdelete);

        v1.setText(db.getusername());
        v2.setText(db.getusermail());
        v3.setText(db.getusercgpa());

        LinearLayout allexams = findViewById(R.id.studenttotalexams);
        allexams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentProfile.this,StudentAllExams.class);
                startActivity(i);
            }
        });

        LinearLayout myexams = findViewById(R.id.Studentregistered);
        myexams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentProfile.this,StudentRegistered.class);
                startActivity(i);
            }
        });

        LinearLayout showdetails = findViewById(R.id.studentdetails);
        showdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.getAllData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Student Id :"+ " "+ res.getString(0)+"\n");
                    buffer.append("First Name :"+ " "+ res.getString(1)+"\n");
                    buffer.append("Middle Name :"+ " "+ res.getString(2)+"\n");
                    buffer.append("Last Name :"+ " "+ res.getString(3)+"\n");
                    buffer.append("Date of Birth :"+ " "+ res.getString(4)+"\n");
                    buffer.append("Phone Number :"+ " "+ res.getString(5)+"\n");
                    buffer.append("Department :"+ " "+ res.getString(6)+"\n");
                    buffer.append("CGPA :"+ " "+ res.getString(7)+"\n");
                    buffer.append("Address :"+ " "+ res.getString(8)+ " "+ res.getString(9)+ " "+ res.getString(10)+"\n\n");

                }

                // Show all data
                showMessage("Data",buffer.toString());
            }

        });

        TextView logout = findViewById(R.id.profilelogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent student_logout = new Intent(getApplicationContext(), MainActivity.class);
                student_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                db.logout();
                startActivity(student_logout);
            }
        });

        LinearLayout editdetails = findViewById(R.id.studenteditdetails);
        editdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentProfile.this,StudentUpdateDetails.class);
                startActivity(intent);
            }
        });

        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Student Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StudentProfile.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String ID = db.getstudentid();
                db.delete(ID);
                startActivity(intent);
            }
        });

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
