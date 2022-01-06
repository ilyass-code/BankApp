package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;


    public DBHelper(Context context) {
        super(context, "Client.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(ID_Client integer primary key,name TEXT ,password TEXT,tel TEXT,email TEXT,montant real)");
        MyDB.execSQL("create Table historique(creation_date datetime primary key,name_receiver TEXT ,name_sender TEXT,montant real,ID_receiver TEXT,ID_sender TEXT)");
        MyDB.execSQL("insert or ignore into users (ID_Client,name,password,tel,email,montant) values (100000000000000,\"admin\",\"admin1\",\"0123456789\",\"admin@gmail.com\",10000) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    @Override
    public synchronized void close() {

        if (database != null)
            database.close();
        super.close();

    }

    public Boolean insertData(String name, String password, String tel, String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("tel", tel);
        contentValues.put("email", email);
        contentValues.put("montant", 100);

        long result = MyDB.insert("users", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public void modifyEmail(String email, String modifiedEmail) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", modifiedEmail);
        MyDB.update("users", cv, "email = ?", new String[]{email});
        MyDB.close();
    }

    public void modifyTel(String email, String modifiedTel) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tel", modifiedTel);
        MyDB.update("users", cv, "email = ?", new String[]{email});
        MyDB.close();
    }

    public void modifyPass(String newPass) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password", newPass);
        MyDB.update("users", cv, "email = ?", new String[]{MainActivity.EMail});
        MyDB.close();
    }

    public Boolean checkUser(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public void addHistorique(String IDSender,String IDReceiver,Double montant,String nameReceiver,String nameSender){
            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("creation_date", String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))));
            contentValues.put("name_receiver", nameReceiver);
            contentValues.put("name_sender", nameSender);
            contentValues.put("montant", montant);
            contentValues.put("ID_receiver", IDReceiver);
            contentValues.put("ID_sender",IDSender);

            long result = MyDB.insert("historique", null, contentValues);
    }

    public boolean deleteUser(String ID)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete("users", "ID_Client = ?", new String[]{ID}) > 0;
    }

    public ArrayList<String> historiqueReceived(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from historique where ID_receiver = ? ORDER BY datetime(0) DESC", new String[]{ID});
        ArrayList<String> buffer = new ArrayList<String>();
        if(cursor.getCount()==0){
            buffer.add("No records found");
        }else if (cursor.moveToFirst()) {
                 while (cursor.moveToNext()){
                     buffer.add(cursor.getString(0)+"\nFrom : "+cursor.getString(2)+" "+cursor.getString(3)+" DHS\n");
                 }
            }
        cursor.close();
        MyDB.close();
        return buffer;
}

    public ArrayList<String> Users(){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users",null);
        ArrayList<String> Users = new ArrayList<String>();
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            Users.add("("+cursor.getString(0)+") Mr. "+cursor.getString(1)+"\nBalance : "+cursor.getString(5)+" DHS\n");
        }
        cursor.close();
        return Users;
    }

    public String UserAccount(String ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where ID_Client = ?",new String[]{ID});
        String User;
        cursor.moveToFirst();
        User =cursor.getString(0)+"\n\nMr. "+cursor.getString(1)+"\n\nTel : "+cursor.getString(3)+"\n\nE-mail : "+cursor.getString(4)+"\n\nBalance : "+Double.parseDouble(cursor.getString(5))+" DHS";
        cursor.close();
        return User;
    }

    public ArrayList<String> historiqueSent(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from historique where ID_sender = ? ORDER BY datetime(0) DESC", new String[]{ID});
        ArrayList<String> buffer = new ArrayList<String>();
        if(cursor.getCount()==0){
            buffer.add("No records found");
        }else{
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                buffer.add(cursor.getString(0)+"\nTo : "+cursor.getString(1)+" "+cursor.getString(3)+" DHS\n");
            }
        }
        cursor.close();
        return buffer;
    }

    public Boolean checkPass(String pass) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor fetch = MyDB.rawQuery("Select * from users where email = ?", new String[]{MainActivity.EMail});
        fetch.moveToFirst();
        if (fetch.getString(2).equals(pass))
            return true;
        else
            return false;
    }

    public Boolean checkTel(String tel) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where tel = ?", new String[]{tel});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String getID(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor fetch = MyDB.rawQuery("Select * from users  where email = ?", new String[]{email});
        fetch.moveToFirst();
        return fetch.getString(0);
    }

    public String getName(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor fetch = MyDB.rawQuery("Select * from users  where email = ?", new String[]{email});
        fetch.moveToFirst();
        String string = fetch.getString(1);
        fetch.close();
        return string;
    }

    public String getTel(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor fetch = MyDB.rawQuery("Select * from users  where email = ?", new String[]{email});
        fetch.moveToFirst();
        return fetch.getString(3);
    }

    public Double getSolde(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor fetch = MyDB.rawQuery("Select * from users  where email = ?", new String[]{email});
        fetch.moveToFirst();
        return Double.parseDouble(fetch.getString(5));
    }

    public Double getSoldeByID(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor fetch = MyDB.rawQuery("Select * from users  where ID_Client = ?", new String[]{ID});
        fetch.moveToFirst();
        return Double.parseDouble(fetch.getString(5));
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public void tranfer(String targetedID,Double montant,Double currMontant){
        Double solde = getSoldeByID(targetedID);
            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            ContentValues values = new ContentValues();
            cv.put("montant", currMontant-montant);
        MyDB.update("users", cv, "email = ?", new String[]{MainActivity.EMail});
        values.put("montant",solde+montant);
        MyDB.update("users", values, "ID_Client = ?", new String[]{targetedID});
        MyDB.close();
    }

    public Boolean checkRIB(String RIB) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where ID_Client = ? ", new String[]{RIB});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
