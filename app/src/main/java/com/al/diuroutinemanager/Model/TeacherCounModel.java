package com.al.diuroutinemanager.Model;

public class TeacherCounModel {

    String name,initial,time,dayOfWeek,uid,rootUid;


    public TeacherCounModel(){

    }
    public TeacherCounModel(String name, String initial, String time, String dayOfWeek,String uid,String rootUid) {
        this.name = name;
        this.initial = initial;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.uid=uid;
        this.rootUid=rootUid;
    }

    public String getRootUid() {
        return rootUid;
    }

    public void setRootUid(String rootUid) {
        this.rootUid = rootUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
