package com.al.diuroutinemanager.Model;

public class TeacherExamRoutineModel {

    String week,room,date,time,teacher,initial,type;


    public TeacherExamRoutineModel(){

    }

    public TeacherExamRoutineModel(String type,String week,String room, String date, String time, String teacher, String initial) {

        this.room = room;
        this.date = date;
        this.time = time;
        this.week=week;
        this.type=type;

        this.teacher = teacher;
        this.initial = initial;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }
}
