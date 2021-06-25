package com.al.diuroutinemanager.Model;

public class TeacherModel {

    public String name,mobile,role,mail,uid,pass,initial;

    public TeacherModel(){

    }

    public TeacherModel(String name, String mobile, String role, String mail, String uid, String pass, String initial) {
        this.name = name;
        this.mobile = mobile;
        this.role = role;
        this.mail = mail;
        this.uid = uid;
        this.pass = pass;
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }
}
