package com.al.diuroutinemanager.Model;

public class ClassModel {

    String match1,uid,match,term,level,section,day,week,course_name,teacher_name,initial,time,room;

    public ClassModel(){

    }

    public ClassModel(String match1,String uid, String match, String term, String level, String section, String day, String week, String course_name, String teacher_name, String initial, String time, String room) {
        this.uid = uid;
        this.match1=match1;
        this.match = match;
        this.term = term;
        this.level = level;
        this.section = section;
        this.day = day;
        this.week = week;
        this.course_name = course_name;
        this.teacher_name = teacher_name;
        this.initial = initial;
        this.time = time;
        this.room = room;
    }

    public String getMatch1() {
        return match1;
    }

    public void setMatch1(String match1) {
        this.match1 = match1;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
