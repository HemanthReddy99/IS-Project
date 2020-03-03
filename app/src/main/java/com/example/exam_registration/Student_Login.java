package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Student_Login extends AppCompatActivity {
EditText e1,e2;
Button b;
DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        db = new DatabaseHelper(this);
        e1 = findViewById(R.id.student_email_login_text_view);
        e2 = findViewById(R.id.student_password_login_text_view);
        b = findViewById(R.id.student_login_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = e1.getText().toString();
                String password = e2.getText().toString();
                Boolean chkmailpass = db.emailpassword(mail,password);
                if(chkmailpass==true){
                    Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Student_Login.this, StudentProfile.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Either Email or Password is incorrect",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
