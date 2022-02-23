package com.example.mycontactlist;

import android.graphics.Bitmap;

import java.util.Calendar;

public class Contact {
    private int contactID;
    private String contactName;
    private String streetaddress;
    private String city;
    private String state;
    private String zipcode;
    private String phoneNumber;
    private String cellNumber;
    private String eMail;
    private Calendar birthday;
    private Bitmap picture;











    public Contact(){
        contactID = -1;
        birthday = Calendar.getInstance();
    }



    public int getContactID() {
        return contactID;
    }

    public void setContactID(int i) {
        contactID = i;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String s) {
        contactName = s;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String s) {
        streetaddress = s;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String s) {
        city = s;
    }

    public String getState() {
        return state;
    }

    public void setState(String s) {
        state = s;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String s) {
        zipcode = s;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String s) {
       phoneNumber = s;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String s) {
      cellNumber = s;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String s) {
        eMail = s;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar c) {
        birthday = c;
    }

    public Bitmap getPicture() {return picture;}

    public void setPicture(Bitmap b) { picture = b;}




}
