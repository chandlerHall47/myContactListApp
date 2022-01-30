package com.example.mycontactlist;

import java.util.Calendar;

public class Contact {


    public int getContactID() {
        return contactID;
    }

    public void setContactID(int i) {
        this.contactID = i;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String s) {
        this.contactName = s;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String s) {
        this.streetaddress = s;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String s) {
        this.city = s;
    }

    public String getState() {
        return state;
    }

    public void setState(String s) {
        this.state = s;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String s) {
        this.zipcode = s;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String s) {
        this.phoneNumber = s;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String s) {
        this.cellNumber = s;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String s) {
        this.eMail = s;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar c) {
        this.birthday = c;
    }

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


    public Contact(){
        contactID = -1;
        birthday = Calendar.getInstance();
    }


}
