package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Teacher_login extends AppCompatActivity {
    EditText e1, e2;
    Button login;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        db = new DatabaseHelper(this);
        e1 = findViewById(R.id.teacher_email_login_text_view);
        e2 = findViewById(R.id.teacher_password_login_text_view);
        login = findViewById(R.id.teacher_login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = e1.getText().toString();
                String password = e2.getText().toString();
                Boolean chkmailpass = db.adminemailpassword(email, password);

                if(chkmailpass==true)
                {
                    Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    Intent teacher_login_to_home = new Intent(Teacher_login.this, TeacherProfile.class);
                    startActivity(teacher_login_to_home);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Either Email or Password is incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
