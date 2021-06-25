package com.al.diuroutinemanager.Model;


public class Empty_Room_Model {

    String week,date,room,time,month,uid;


    public Empty_Room_Model(){

    }

    public Empty_Room_Model(String week, String date, String room, String time, String month,String uid) {
        this.week = week;
        this.date = date;
        this.room = room;
        this.time = time;
        this.month = month;
        this.uid=uid;

    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
