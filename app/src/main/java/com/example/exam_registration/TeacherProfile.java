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

public class TeacherProfile extends AppCompatActivity {
    TextView v1,v2,v3,v4;
    DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        v1 = findViewById(R.id.profilename);
        v2 = findViewById(R.id.profileemail);
        v3 = findViewById(R.id.profilephno);
        v4 = findViewById(R.id.teacherdelete);

        v1.setText(db.admingetusername());
        v2.setText(db.admingetusermail());
        v3.setText(db.admingetuserphno());

        LinearLayout showdetails = findViewById(R.id.studentdetails);
        showdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.admingetAllData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Teacher Id :"+ " "+ res.getString(0)+"\n");
                    buffer.append("First Name :"+ " "+ res.getString(1)+"\n");
                    buffer.append("Middle Name :"+ " "+ res.getString(2)+"\n");
                    buffer.append("Last Name :"+ " "+ res.getString(3)+"\n");
                    buffer.append("Date of Birth :"+ " "+ res.getString(4)+"\n");
                    buffer.append("Phone Number :"+ " "+ res.getString(5)+"\n");
                    buffer.append("Department :"+ " "+ res.getString(6)+"\n");
                    //buffer.append("CGPA :"+ res.getString(7)+"\n");
                    buffer.append("Address :"+ " "+ res.getString(7)+ " "+ res.getString(8)+ " "+ res.getString(9)+"\n\n");

                }

                // Show all data
                showMessage("Details",buffer.toString());
            }

        });

        LinearLayout addExam  = findViewById(R.id.studenttotalexams);
        addExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TeacherAddExam.class);
                startActivity(intent);
            }
        });

        LinearLayout yourExam = findViewById(R.id.Studentregistered);
        yourExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Teacher_YourExam.class);
                startActivity(intent);
            }
        });

        TextView logout = findViewById(R.id.profilelogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin_logout = new Intent(getApplicationContext(), MainActivity.class);
                admin_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                db.adminlogout();
                startActivity(admin_logout);
            }
        });

        LinearLayout editdetails = findViewById(R.id.studenteditdetails);
        editdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherProfile.this,TeacherUpdateDetails.class);
                startActivity(intent);
            }
        });

        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Teacher Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherProfile.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String ID = db.admingetuserid();
                db.admindelete(ID);
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
