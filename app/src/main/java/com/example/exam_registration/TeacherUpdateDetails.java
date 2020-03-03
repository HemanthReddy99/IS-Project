package com.example.exam_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherUpdateDetails extends AppCompatActivity {

    DatabaseHelper myDb = new DatabaseHelper(this);

    EditText Smail,Sfname,Smname,Slname,Sdob,Sphno,Sdept,SID,Scgpa,Sadd1,Sadd2,Sadd3,Spass,Srepass;
    Button Sregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update_details);

        //Smail = findViewById(R.id.email_teacher_update_text_view);
        Sfname = findViewById(R.id.first_name_teacher_update_text_view);
        Smname = findViewById(R.id.middle_name_teacher_update_text_view);
        Slname = findViewById(R.id.last_name_teacher_update_text_view);
        Sdob = findViewById(R.id.dob_teacher_update_text_view);
        Sphno = findViewById(R.id.phone_teacher_update_text_view);
        Sdept = findViewById(R.id.department_teacher_update_text_view);
        //SID = findViewById(R.id.rollno_teacher_update_text_view);
        //Scgpa = findViewById(R.id.cgpa_teacher_update_text_view);
        Sadd1 = findViewById(R.id.addr1_teacher_update_text_view);
        Sadd2 = findViewById(R.id.addr2_teacher_update_text_view);
        Sadd3 = findViewById(R.id.addr3_teacher_update_text_view);
        //Spass = findViewById(R.id.password_teacher_update_text_view);
        //Srepass = findViewById(R.id.password_reenter_teacher_update_text_view);

        Sregister = findViewById(R.id.update_teacher_button);

        Sregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String email = Smail.getText().toString();
                String fname = Sfname.getText().toString();
                String mname = Smname.getText().toString();
                String lname = Slname.getText().toString();
                String dob = Sdob.getText().toString();
                String phno = Sphno.getText().toString();
                String dept = Sdept.getText().toString();
                //String rollno = SID.getText().toString();
               // String cgpa  = Scgpa.getText().toString();
                String add1 = Sadd1.getText().toString();
                String add2 = Sadd2.getText().toString();
                String add3 = Sadd3.getText().toString();
                // String pass = Spass.getText().toString();
                //String repass = Srepass.getText().toString();

                if(fname.equals("") || mname.equals("") || lname.equals("") || dob.equals("") || phno.equals("") || dept.equals("") ||  add1.equals("") || add2.equals("") || add3.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "One or more fields empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // if(pass.equals(repass))
                    // {
                    //   Boolean chkidmail = myDb.chkidmail(rollno, email);

                    // if(chkidmail==true)
                    // {
                    myDb.adminupdateData(fname,mname,lname,dob,phno,dept,add1,add2,add3);
                    //                              myDb.getAllData();
                    // if(insert==true)
                    // {
                    Toast.makeText(getApplicationContext(),"Updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent suc_reg =new Intent(getBaseContext(), TeacherProfile.class);
                    //suc_reg.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(suc_reg);
                    // }
                    // }
                    // else
                    // {
                    //     Toast.makeText(getApplicationContext(), "Email or teacher already exists", Toast.LENGTH_SHORT).show();
                    // }

                    // }
                    //else{
                    //  Toast.makeText(getApplicationContext(), "Passwords do not Match", Toast.LENGTH_SHORT).show();

                    //}

                }
            }
        });

    }
}
