package com.bcis.chamena.model;

public class User{
   public String id;
    public  String fullName;
    public String phoneNumber ;
    public String collegeOffice ;
    public String email;
    public String password ;
    public String confirmPassword;
    public boolean isAdmin;

    public User(String id, String fullName, String phoneNumber, String collegeOffice, String email, String password, String confirmPassword) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.collegeOffice = collegeOffice;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User(String id, String fullName, String phoneNumber, String collegeOffice, String email, String password, String confirmPassword, boolean isAdmin) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.collegeOffice = collegeOffice;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isAdmin = isAdmin;
    }
}