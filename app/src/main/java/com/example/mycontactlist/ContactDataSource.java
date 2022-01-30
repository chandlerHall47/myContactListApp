package com.example.mycontactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContactDataSource {

    private SQLiteDatabase database;
    private ContactDBHelper dbHelper;

    public ContactDataSource(Context context){

        dbHelper = new ContactDBHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }



    public void close(){
        dbHelper.close();
    }




    public boolean insertContact(Contact c){
        boolean didSucceed = false;

        ContentValues InitialValues = new ContentValues();

        try{
            InitialValues.put("contactname", c.getContactName());
            InitialValues.put("streetaddress", c.getStreetaddress());
            InitialValues.put("city", c.getCity());
            InitialValues.put("state", c.getState());
            InitialValues.put("zipcode", c.getZipcode());
            InitialValues.put("phonenumber", c.getPhoneNumber());
            InitialValues.put("cellnumber", c.getCellNumber());
            InitialValues.put("email", c.geteMail());
            InitialValues.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));

            didSucceed = database.insert("contact", null, InitialValues) > 0;


        }
        catch(Exception e){
            //Do nothing, will return false if there is an exception
        }
        return didSucceed;
    }

public boolean updateContact(Contact c){

        boolean didSucceed = false;

        try{
            Long rowID = (long) c.getContactID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("contactname", c.getContactName());
            updateValues.put("streetaddress", c.getStreetaddress());
            updateValues.put("city", c.getCity());
            updateValues.put("state", c.getState());
            updateValues.put("zipcode", c.getZipcode());
            updateValues.put("phonenumber", c.getPhoneNumber());
            updateValues.put("cellnumber", c.getCellNumber());
            updateValues.put("email", c.geteMail());
            updateValues.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));

            didSucceed = database.update("contact", updateValues, "_id=" + rowID, null) > 0;

    }
        catch(Exception e){
            //Nothing, same as last exception
        }
        return didSucceed;
}


public int getLastContactID(){
    int lastID;

    try{
        String query = "Select MAX(_id) from contact";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        lastID = cursor.getInt(0);
        cursor.close();

    }
    catch (Exception e){
        lastID = -1;
    }
    return lastID;

}




}
