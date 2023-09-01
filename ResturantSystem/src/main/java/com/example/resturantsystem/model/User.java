package com.example.resturantsystem.model;

public class User {
    private String Role;
    private int _Id;
    private String userName;
    private String userPass;
    private float auth_Disc;
    public User(String userName,String userPass,float auth_Disc,String Role){

        this.auth_Disc=auth_Disc;
        this.userName=userName;
        this.userPass=userPass;
        this.Role=Role;
    }
    public User(int _id,String userName,String userPass,float auth_Disc,String Role){
        this._Id=_id;
        this.auth_Disc=auth_Disc;
        this.userName=userName;
        this.userPass=userPass;
        this.Role=Role;
    }
    public float getAuth_Disc() {
        return auth_Disc;
    }

    public void setAuth_Disc(float auth_Disc) {
        this.auth_Disc = auth_Disc;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public int get_Id() {
        return _Id;
    }
}
