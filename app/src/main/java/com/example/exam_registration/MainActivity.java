package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //On Click Feature to go from main to student login
        CardView student_login = findViewById(R.id.studentlogin);

        student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_to_stu_login = new Intent(getBaseContext(), Student_Login.class);
                startActivity(main_to_stu_login);
            }
        });

        //On Click Feature to go from main to teacher login
        CardView teacher_login = findViewById(R.id.teacherlogin);

        teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_to_teacher_login = new Intent(getBaseContext(), Teacher_login.class);
                startActivity(main_to_teacher_login);
            }
        });

        //On Click Feature to go from main to student registration

        CardView student_registration = findViewById(R.id.studentregistration);

        student_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_to_student_registration = new Intent(getBaseContext(), Student_registration.class);
                startActivity(main_to_student_registration);
            }
        });

        //On Click Feature to go from main to teacher registration

        CardView teacher_registration = findViewById(R.id.teacherregistration);

        teacher_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_to_teacher_registration = new Intent(getBaseContext(), Teacher_registration.class);
                startActivity(main_to_teacher_registration);
            }
        });


    }
}
