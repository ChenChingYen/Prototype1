package com.example.prototype;

public class ModuleClasstmp {
    private String courseCode;
    private int occ;
    private int day;
    private int startTime;
    private int endTime;

    ModuleClasstmp(String courseCode, int occ, int day, int startTime, int endTime){
        this.courseCode = courseCode;
        this.occ = occ;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourseCode(){
        return courseCode;
    }

    public int getOcc(){
        return occ;
    }

    public int getDay(){
        return day;
    }

    public int getStartTime(){
        return startTime;
    }

    public int getEndTime(){
        return endTime;
    }

}
