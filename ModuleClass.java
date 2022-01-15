package com.example.prototype;

public class ModuleClass {
    String courseCode;
    String courseName;
    int credit;
    int occ;
    String activityType;
    String staffName;
    String day;
    String timeString;
    int target;
    int actual;

    public ModuleClass(String courseCode, String courseName, int credit, int occ, String activityType, String staffName, String day, String timeString, int target, int actual){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.occ = occ;
        this.activityType = activityType;
        this.staffName = staffName;
        this.day = day;
        this.timeString = timeString;
        this.target = target;
        this.actual = actual;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public int getOcc() {
        return occ;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getDay() {
        return day;
    }

    public String getTimeString() {
        return timeString;
    }

    public int getTarget() {
        return target;
    }

    public int getActual() {
        return actual;
    }

}
