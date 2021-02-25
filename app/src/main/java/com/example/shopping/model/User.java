package com.example.shopping.model;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User extends BaseObservable {


    private String username;
    private String password;
    @PrimaryKey
    @NonNull
    private String emailId;
    private String phoneNo;
    private String address;

    public User(String username, String password, @NonNull String emailId, String phoneNo, String address) {
        this.username = username;
        this.password = password;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.address = address;
    }


    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Bindable
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(@NonNull String emailId) {
        this.emailId = emailId;
    }

    @Bindable
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
