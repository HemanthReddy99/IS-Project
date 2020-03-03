package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User_Login extends AppCompatActivity {
EditText e1,e2;
Button b;
DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
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
                    String check = db.getuserroleforlogin(mail).toString();
                    //Toast.makeText(getApplicationContext(),check,Toast.LENGTH_SHORT).show();
                    if(check.compareTo("Student")==0){
                        Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(User_Login.this, StudentProfile.class);
                        startActivity(intent);
                    }
                    else if(db.getuserroleforlogin(mail).equals("Teacher")){
                        Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(User_Login.this, TeacherProfile.class);
                        startActivity(intent);
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Either Email or Password is incorrect",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
