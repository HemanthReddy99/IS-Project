package com.example.exam_registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Info.db";
    public static final String TABLE_NAME = "Student";
    public static final String COL_1 = "SID";
    public static final String COL_2 = "Smail";
    public static final String COL_3 = "Spass";
    public static final String COL_4 = "Sfname";
    public static final String COL_5 = "Smname";
    public static final String COL_6 = "Slname";
    public static final String COL_7 = "Sdob";
    public static final String COL_8 = "Sphno";
    public static final String COL_9 = "Sdept";
    public static final String COL_10 = "Scgpa";
    public static final String COL_11 = "Sadd1";
    public static final String COL_12 = "Sadd2";
    public static final String COL_13 = "Sadd3";
    

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Student (SID text PRIMARY KEY, Sfname text, Smname text, Slname text, Sdob text, Sphno text, Sdept text, Scgpa text, Sadd1 text, Sadd2 text, Sadd3 text, loggedin text)");
        db.execSQL("create table StudentLogin (SID text, Smail text, Spass text, loggedin text, PRIMARY KEY (SID,Smail), FOREIGN KEY (SID) REFERENCES Student(SID) on delete cascade on update cascade)");
        db.execSQL("create table Admin (AID text PRIMARY KEY, Afname text, Amname text, Alname text, Adob text, Aphno text, Adept text, Aadd1 text, Aadd2 text, Aadd3 text, loggedin text)");
        db.execSQL("create table AdminLogin (AID text, Amail text, Apass text, loggedin text, PRIMARY KEY (AID,Amail), FOREIGN KEY (AID) REFERENCES Administrator(AID) on delete cascade on update cascade)");
        db.execSQL("create table Exams (EID text PRIMARY KEY, AID text, Ename text, Edate text, Eduration text, FOREIGN KEY (AID) REFERENCES Admin(AID)on delete cascade on update cascade)");
        db.execSQL("create table Applies (SID text, EID text, PRIMARY KEY(SID, EID), FOREIGN KEY (SID) REFERENCES Student(SID) on delete cascade on update cascade, FOREIGN KEY (EID) REFERENCES Exams(EID) on delete cascade on update cascade)");
        db.execSQL("create table Registrations (SID text, AID text, EID text, Status text, PRIMARY KEY(SID, AID, EID), FOREIGN KEY (SID) REFERENCES Student(SID) on delete cascade on update cascade, FOREIGN KEY (AID) REFERENCES Admin(AID) on delete cascade on update cascade, FOREIGN KEY (EID) REFERENCES Exams(EID) on delete cascade on update cascade)");







    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Student");
        db.execSQL("DROP TABLE IF EXISTS StudentLogin");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS AdminLogin");









        onCreate(db);
    }

    public boolean insertData(String sid,String mail,String pass, String fname, String mname, String lname, String dob, String phno, String dept, String cgpa, String add1, String add2, String add3 ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SID",sid);
        contentValues.put("Sfname",fname);
        contentValues.put("Smname",mname);
        contentValues.put("Slname",lname);
        contentValues.put("Sdob", dob);
        contentValues.put("Sphno", phno);
        contentValues.put("Sdept", dept);
        contentValues.put("Scgpa", cgpa);
        contentValues.put("Sadd1", add1);
        contentValues.put("Sadd2", add2);
        contentValues.put("Sadd3", add3);
        contentValues.put("loggedin", "no");

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("SID", sid);
        contentValues1.put("Smail", mail);
        contentValues1.put("Spass", pass);
        contentValues1.put("loggedin", "no");

        long result = db.insert("Student",null ,contentValues);
        long result1 = db.insert("StudentLogin", null, contentValues1);
        return result != -1 && result1 != -1;
    }


    public Boolean chkidmail(String sid, String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Student where SID=?", new String[]{sid});
        Cursor cursor1 = db.rawQuery("SELECT * from StudentLogin where Smail=?", new String[]{email});

        return cursor.getCount() <= 0 && cursor1.getCount() <= 0;
    }

    public Boolean emailpassword(String email,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from StudentLogin where Smail=? and Spass=?",new String[]{email,password});
        if(cursor.getCount()>0){
            SQLiteDatabase mydb = this.getWritableDatabase();
            mydb.execSQL("update StudentLogin set loggedin = 'yes' where Smail=?",new String[]{email});
            mydb.execSQL("update Student set loggedin = 'yes' where Student.SID = (select SID from StudentLogin where loggedin ='yes')");
            return true;
        }
        else {
            return false;
        }
    }

    public String getusername()throws SQLException{
        String username = "";
        String tablename = "Student";
        String keyid = "SID";
        String keyname = "Sfname";
        Cursor cursor = this.getReadableDatabase().query(tablename,new String[]{keyid,keyname},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                username = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return username;
    }

    public String getusermail()throws SQLException{
        String usermail = "";
        String tablename = "StudentLogin";
        String keyid = "SID";
        String keyname = "Smail";
        Cursor cursor = this.getReadableDatabase().query(tablename,new String[]{keyid,keyname},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                usermail = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return usermail;
    }

    public String getstudentid()throws SQLException{
        String usermail = "";
        String tablename = "StudentLogin";
        String keyid = "SID";
        String keyname = "Smail";
        Cursor cursor = this.getReadableDatabase().query(tablename,new String[]{keyid,keyname},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                usermail = cursor.getString(0);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return usermail;
    }

    public String getusercgpa()throws SQLException{
        String usercgpa = "";
        String tablename = "Student";
        String keyid = "SID";
        String keyname = "Scgpa";
        Cursor cursor = this.getReadableDatabase().query(tablename,new String[]{keyid,keyname},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                usercgpa = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return usercgpa;
    }

    public void logout(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update Student set loggedin = 'no' where loggedin = 'yes'");
        db.execSQL("update StudentLogin set loggedin = 'no' where loggedin = 'yes'");

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = this.getReadableDatabase().rawQuery("select * from Student where loggedin = ?",new String[]{"yes"});
        return res;
    }

    public void updateData(String fname, String mname, String lname, String dob, String phno, String dept, String cgpa, String add1, String add2, String add3){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update Student set Sfname = ? where loggedin = 'yes'",new String[]{fname});
        db.execSQL("update Student set Smname = ? where loggedin = 'yes'",new String[]{mname});
        db.execSQL("update Student set Slname = ? where loggedin = 'yes'",new String[]{lname});
        db.execSQL("update Student set Sdob = ? where loggedin = 'yes'",new String[]{dob});
        db.execSQL("update Student set Sphno = ? where loggedin = 'yes'",new String[]{phno});
        db.execSQL("update Student set Sdept = ? where loggedin = 'yes'",new String[]{dept});
        db.execSQL("update Student set Scgpa = ? where loggedin = 'yes'",new String[]{cgpa});
        db.execSQL("update Student set Sadd1 = ? where loggedin = 'yes'",new String[]{add1});
        db.execSQL("update Student set Sadd2 = ? where loggedin = 'yes'",new String[]{add2});
        db.execSQL("update Student set Sadd3 = ? where loggedin = 'yes'",new String[]{add3});

       // return true;
    }
//
//    public boolean updateData(String id,String name,String surname,String marks) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,id);
//        contentValues.put(COL_2,name);
//        contentValues.put(COL_3,surname);
//        contentValues.put(COL_4,marks);
//        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
//        return true;
//    }
//
    public void delete (String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Student","SID = ? ",new String[]{ID});
        db.delete("StudentLogin","SID = ?", new String[]{ID});
        db.delete("Applies","SID = ?", new String[]{ID});
        db.delete("Registrations","SID = ?", new String[]{ID});
        //db.execSQL("delete from Student where loggedin = ?",new String[]{"yes"});
//        db.execSQL("delete from StudentLogin where loggedin = ?",new String[]{"yes"});
//        db.execSQL("delete from Applies where SID = ?",new String[]{ID});
//        db.execSQL("delete from Registrations where SID = ?",new String[]{ID});
        //return;
    }

































































































































































































































































































































    public boolean insertAdminData(String aid, String mail, String pass, String fname, String mname, String lname, String dob, String phno, String dept, String add1, String add2, String add3)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("AID",aid);
        contentValues.put("Afname",fname);
        contentValues.put("Amname",mname);
        contentValues.put("Alname",lname);
        contentValues.put("Adob", dob);
        contentValues.put("Aphno", phno);
        contentValues.put("Adept", dept);
        contentValues.put("Aadd1", add1);
        contentValues.put("Aadd2", add2);
        contentValues.put("Aadd3", add3);
        contentValues.put("loggedin", "no");

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("AID", aid);
        contentValues1.put("Amail", mail);
        contentValues1.put("Apass", pass);
        contentValues1.put("loggedin", "no");

        long result = db.insert("Admin",null ,contentValues);
        long result1 = db.insert("AdminLogin", null, contentValues1);
        return result != -1 && result1 != -1;
    }

    public Boolean chkadminidmail(String aid, String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Admin where AID=?", new String[]{aid});
        Cursor cursor1 = db.rawQuery("SELECT * from AdminLogin where Amail=?", new String[]{email});
        return cursor.getCount() <= 0 && cursor1.getCount() <= 0;
    }

    public Boolean adminemailpassword(String email,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from AdminLogin where Amail=? and Apass=?",new String[]{email,password});
        if(cursor.getCount()>0){
            SQLiteDatabase mydb = this.getWritableDatabase();
            mydb.execSQL("update AdminLogin set loggedin = 'yes' where Amail=?",new String[]{email});
            mydb.execSQL("update Admin set loggedin = 'yes' where Admin.AID = (select AID from AdminLogin where loggedin ='yes')");
            return true;
        }
        else {
            return false;
        }
    }

    public String admingetusername()throws SQLException{
        String username = "";
        String tablename = "Admin";
        String keyid = "AID";
        String keyname = "Afname";
        Cursor cursor = this.getReadableDatabase().query(tablename,new String[]{keyid,keyname},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                username = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return username;
    }

    public String admingetusermail()throws SQLException{
        String usermail = "";
        String tablename = "AdminLogin";
        String keyid = "AID";
        String keyname = "Amail";
        Cursor cursor = this.getReadableDatabase().query(tablename,new String[]{keyid,keyname},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                usermail = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return usermail;
    }

    public String admingetuserid()throws SQLException{
        String usermail = "";
        String tablename = "AdminLogin";
        String keyid = "AID";
        String keyname = "Amail";
        Cursor cursor = this.getReadableDatabase().query(tablename,new String[]{keyid,keyname},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                usermail = cursor.getString(0);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return usermail;
    }


    public String admingetuserphno()throws SQLException{
        String username = "";
        String tablename = "Admin";
        String keyid = "AID";
        String keyname = "Aphno";
        Cursor cursor = this.getReadableDatabase().query(tablename,new String[]{keyid,keyname},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                username = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return username;
    }

    public void adminlogout(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update Admin set loggedin = 'no' where loggedin = 'yes'");
        db.execSQL("update AdminLogin set loggedin = 'no' where loggedin = 'yes'");

    }

    public Cursor admingetAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = this.getReadableDatabase().rawQuery("select * from Admin where loggedin = ?",new String[]{"yes"});
        return res;
    }

    public void adminupdateData(String fname, String mname, String lname, String dob, String phno, String dept, String add1, String add2, String add3){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update Admin set Afname = ? where loggedin = 'yes'",new String[]{fname});
        db.execSQL("update Admin set Amname = ? where loggedin = 'yes'",new String[]{mname});
        db.execSQL("update Admin set Alname = ? where loggedin = 'yes'",new String[]{lname});
        db.execSQL("update Admin set Adob = ? where loggedin = 'yes'",new String[]{dob});
        db.execSQL("update Admin set Aphno = ? where loggedin = 'yes'",new String[]{phno});
        db.execSQL("update Admin set Adept = ? where loggedin = 'yes'",new String[]{dept});
       // db.execSQL("update Admin set Acgpa = ? where loggedin = 'yes'",new String[]{cgpa});
        db.execSQL("update Admin set Aadd1 = ? where loggedin = 'yes'",new String[]{add1});
        db.execSQL("update Admin set Aadd2 = ? where loggedin = 'yes'",new String[]{add2});
        db.execSQL("update Admin set Aadd3 = ? where loggedin = 'yes'",new String[]{add3});

        // return true;
    }

    public Boolean checkExamId(String eid)
    {
        Cursor cursor = this.getReadableDatabase().rawQuery("Select * from Exams where EID=?",new String[]{eid});

        return cursor.getCount() <= 0;
    }

    public boolean insertExamData(String eid, String ename, String edate, String eduration)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String aid = "";
        Cursor cursor = this.getReadableDatabase().query("Admin",new String[]{"AID","AID"},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                aid = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put("EID", eid);
        contentValues.put("AID", aid);
        contentValues.put("Ename", ename);
        contentValues.put("Edate", edate);
        contentValues.put("Eduration", eduration);

        long result = db.insert("Exams",null ,contentValues);
        return result != -1;
    }

    public List<Exam> getAllExams()
    {
        List<Exam> examList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Exams",
                new String[]{"EID", "AID", "Ename","Edate", "Eduration"},
                null,
                null,
                null,
                null,
                "Ename ASC");

        if(cursor.moveToFirst())
        {
            do {
                Exam exam = new Exam();
                exam.setEid(cursor.getString(cursor.getColumnIndex("EID")));
                exam.setAid(cursor.getString(cursor.getColumnIndex("AID")));
                exam.setEname(cursor.getString(cursor.getColumnIndex("Ename")));
                exam.setEdate(cursor.getString(cursor.getColumnIndex("Edate")));
                exam.setEduration(cursor.getString(cursor.getColumnIndex("Eduration")));

                examList.add(exam);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return examList;
    }

    public String get_admin_name_for_exam(String aid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = "Not Found";

        String query = "select Afname from Admin where AID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{aid});

        if(cursor.moveToFirst()){
            do {
                username = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return username;

    }

    public String getsid()
    {
        String sid = "";

        Cursor cursor = this.getReadableDatabase().query("Student",new String[]{"SID","SID"},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                sid = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return sid;
    }

    public boolean insert_student_applied(String sid, String eid, String aid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues1 = new ContentValues();

        contentValues.put("SID", sid);
        contentValues.put("EID", eid);

        contentValues1.put("SID", sid);
        contentValues1.put("AID", aid);
        contentValues1.put("EID", eid);
        contentValues1.put("Status", "NOT ACCEPTED");

        long result = db.insert("Applies",null, contentValues);
        long result1 = db.insert("Registrations", null, contentValues1);

        return result != -1 && result1 != -1;

    }

    public List<RegisteredExam> getRegisteredExam()
    {
        List<RegisteredExam> registeredExamList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sid = "";

        Cursor cursor = this.getReadableDatabase().query("Student",new String[]{"SID","SID"},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                sid = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        String query_to_be_given = "select Exams.EID, Ename, Edate, Eduration, Status from Exams,Registrations where Exams.EID = Registrations.EID AND Registrations.SID = ?";

        Cursor queryview = db.rawQuery(query_to_be_given, new String[]{sid});

        if(queryview.moveToFirst())
        {
            do{
                RegisteredExam registeredExam = new RegisteredExam();

                registeredExam.setEid(queryview.getString(queryview.getColumnIndex("EID")));
                registeredExam.setExamName(queryview.getString(queryview.getColumnIndex("Ename")));
                registeredExam.setExamDate(queryview.getString(queryview.getColumnIndex("Edate")));
                registeredExam.setExamDuration(queryview.getString(queryview.getColumnIndex("Eduration")));
                registeredExam.setStatus(queryview.getString(queryview.getColumnIndex("Status")));

                registeredExamList.add(registeredExam);
            }while(queryview.moveToNext());
        }

        queryview.close();
        cursor.close();
        db.close();

        return registeredExamList;
    }

    public List<TeacherExam> getTeacherExams()
    {
        List<TeacherExam> teacherExamList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String aid = "";

        Cursor cursor = this.getReadableDatabase().query("Admin",new String[]{"AID","AID"},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                aid = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        String query_to_be_given = "select Exams.EID, Student.SID, Sfname, Smail, Ename, Edate from Student,StudentLogin,Exams,Registrations where Exams.EID = Registrations.EID AND Student.SID = Registrations.SID AND Student.SID = StudentLogin.SID AND Registrations.AID=?";

        Cursor query = db.rawQuery(query_to_be_given, new String[]{aid});

        if(query.moveToFirst())
        {
            do{
                TeacherExam teacherExam = new TeacherExam();

                teacherExam.setEid(query.getString(query.getColumnIndex("EID")));
                teacherExam.setSid(query.getString(query.getColumnIndex("SID")));
                teacherExam.setSname(query.getString(query.getColumnIndex("Sfname")));
                teacherExam.setSmail(query.getString(query.getColumnIndex("Smail")));
                teacherExam.setEname(query.getString(query.getColumnIndex("Ename")));
                teacherExam.setEdate(query.getString(query.getColumnIndex("Edate")));

                teacherExamList.add(teacherExam);
            }while(query.moveToNext());
        }

        query.close();
        cursor.close();
        db.close();

        return teacherExamList;
    }

    public void teacherApproveExam(String sid, String eid)
    {
        String aid = "";

        Cursor cursor = this.getReadableDatabase().query("Admin",new String[]{"AID","AID"},"loggedin = 'yes'",null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                aid = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update Registrations set Status = 'Approved' where AID=? AND SID=? AND EID=? AND Status = 'NOT ACCEPTED'", new String[]{aid,sid,eid});

    }

    public void admindelete (String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Admin","AID = ? ",new String[]{ID});
        db.delete("AdminLogin","AID = ?", new String[]{ID});
        //db.delete("Applies","AID = ?", new String[]{ID});
        db.delete("Registrations","AID = ?", new String[]{ID});
        //db.execSQL("delete from Student where loggedin = ?",new String[]{"yes"});
//        db.execSQL("delete from StudentLogin where loggedin = ?",new String[]{"yes"});
//        db.execSQL("delete from Applies where SID = ?",new String[]{ID});
//        db.execSQL("delete from Registrations where SID = ?",new String[]{ID});
        //return;
    }

    public void admindeleteexam (String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete("Admin","AID = ? ",new String[]{ID});
        //db.delete("AdminLogin","AID = ?", new String[]{ID});
        //db.delete("Applies","AID = ?", new String[]{ID});
        db.delete("Exams","EID = ?", new String[]{ID});
        db.delete("Applies","EID = ?", new String[]{ID});
        db.delete("Registrations","EID = ?", new String[]{ID});
        //db.execSQL("delete from Student where loggedin = ?",new String[]{"yes"});
//        db.execSQL("delete from StudentLogin where loggedin = ?",new String[]{"yes"});
//        db.execSQL("delete from Applies where SID = ?",new String[]{ID});
//        db.execSQL("delete from Registrations where SID = ?",new String[]{ID});
        //return;
    }


}
