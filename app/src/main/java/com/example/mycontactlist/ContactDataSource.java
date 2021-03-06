package com.example.mycontactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

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

            if(c.getPicture() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    c.getPicture().compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] photo = baos.toByteArray();
                    InitialValues.put("contactphoto", photo);
            }
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


            if (c.getPicture() != null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                c.getPicture().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photo = baos.toByteArray();
                updateValues.put("contactphoto", photo);
            }
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


public ArrayList<String> getContactName(){
        ArrayList<String> contactNames = new ArrayList<>();

        try{
            String query = "Select contactname from contact";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while(!cursor.isAfterLast() ){
                contactNames.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e){
            contactNames = new ArrayList<>();
        }
        return contactNames;

}



public ArrayList<Contact> getContacts(String sortField, String sortOrder){

        ArrayList<Contact> contacts = new ArrayList<>();



        try{
            String query = " SELECT * FROM contact ORDER BY " + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query,null);

            Contact newContact;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                newContact = new Contact();
                newContact.setContactID(cursor.getInt(0));
                newContact.setContactName(cursor.getString(1));
                newContact.setStreetaddress(cursor.getString(2));
                newContact.setCity(cursor.getString(3));
                newContact.setState(cursor.getString(4));
                newContact.setZipcode(cursor.getString(5));
                newContact.setPhoneNumber(cursor.getString(6));
                newContact.setCellNumber(cursor.getString(7));
                newContact.seteMail(cursor.getString(8));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(9)));
                contacts.add(newContact);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e){
            contacts = new ArrayList<>();
        }
        return contacts;
}


public Contact getSpecificContact(int contactId){

        Contact contact = new Contact();

        String query = "SELECT * FROM contact WHERE _id = " + contactId;
        Cursor cursor = database.rawQuery(query, null);


        if(cursor.moveToFirst()) {
            contact.setContactID(cursor.getInt(0));
            contact.setContactName(cursor.getString(1));
            contact.setStreetaddress(cursor.getString(2));
            contact.setCity(cursor.getString(3));
            contact.setState(cursor.getString(4));
            contact.setZipcode(cursor.getString(5));

            contact.setPhoneNumber(cursor.getString(6));
            contact.setCellNumber(cursor.getString(7));
            contact.seteMail(cursor.getString(8));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(9)));
            contact.setBirthday(calendar);
            byte[] photo = cursor.getBlob(10);
            if(photo != null){
                ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                Bitmap thePicture = BitmapFactory.decodeStream(imageStream);
                contact.setPicture(thePicture);
            }
            cursor.close();
        }
        return contact;
}


public boolean deleteContact(int contactId){
        boolean didDelete = false;
        try{
            didDelete = database.delete("contact", " _id= " + contactId ,null) > 0;
        } catch (Exception e){
                //Does nothing
        }
        return didDelete;
}



}
