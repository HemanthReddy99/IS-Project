package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Teacher_registration extends AppCompatActivity {

    DatabaseHelper adminDb;
    EditText Amail,Afname,Amname,Alname,Adob,Aphno,Adept,AID,Aadd1,Aadd2,Aadd3,Apass,Arepass;
    Button Aregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        adminDb = new DatabaseHelper(this);

        Amail = findViewById(R.id.email_admin_registration_text_view);
        Afname = findViewById(R.id.first_name_admin_registration_text_view);
        Amname = findViewById(R.id.middle_name_admin_registration_text_view);
        Alname = findViewById(R.id.last_name_admin_registration_text_view);
        Adob = findViewById(R.id.dob_admin_registration_text_view);
        Aphno = findViewById(R.id.phone_admin_registration_text_view);
        Adept = findViewById(R.id.department_admin_registration_text_view);
        AID = findViewById(R.id.rollno_admin_registration_text_view);
        Aadd1 = findViewById(R.id.addr1_admin_registration_text_view);
        Aadd2 = findViewById(R.id.addr2_admin_registration_text_view);
        Aadd3 = findViewById(R.id.addr3_admin_registration_text_view);
        Apass = findViewById(R.id.password_admin_registration_text_view);
        Arepass = findViewById(R.id.password_reenter_admin_registration_text_view);

        Aregister = findViewById(R.id.register_admin_button);

        Aregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Amail.getText().toString();
                String fname = Afname.getText().toString();
                String mname = Amname.getText().toString();
                String lname = Alname.getText().toString();
                String dob = Adob.getText().toString();
                String phno = Aphno.getText().toString();
                String dept = Adept.getText().toString();
                String rollno = AID.getText().toString();
                String add1 = Aadd1.getText().toString();
                String add2 = Aadd2.getText().toString();
                String add3 = Aadd3.getText().toString();
                String pass = Apass.getText().toString();
                String repass = Arepass.getText().toString();


                if(email.equals("") || fname.equals("") || mname.equals("") || lname.equals("") || dob.equals("") || phno.equals("") || dept.equals("") || rollno.equals("") || add1.equals("") || add2.equals("") || add3.equals("") || pass.equals("") || repass.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "One or more fields empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pass.equals(repass))
                    {
                        Boolean chkidmail = adminDb.chkadminidmail(rollno, email);

                        if(chkidmail==true)
                        {
                            Boolean insert = adminDb.insertAdminData(rollno,email,pass,fname,mname,lname,dob,phno,dept,add1,add2,add3);

                            if(insert==true)
                            {
                                Intent admin_suc_reg =new Intent(getBaseContext(), MainActivity.class);
                                startActivity(admin_suc_reg);
                                Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Email or Admin already exists", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Passwords do not Match", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });

    }
}
