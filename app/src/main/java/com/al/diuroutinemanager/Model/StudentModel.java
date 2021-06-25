package com.al.diuroutinemanager.Model;

public class StudentModel {

    String name,id,role,uid,mail,pass,section,level,term,shift,match;

    public StudentModel(){

    }

    public StudentModel(String name, String id, String role, String uid, String mail, String pass, String section, String level, String term, String shift, String match) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.uid = uid;
        this.mail = mail;
        this.pass = pass;
        this.section = section;
        this.level = level;
        this.term = term;
        this.shift = shift;
        this.match = match;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
}
