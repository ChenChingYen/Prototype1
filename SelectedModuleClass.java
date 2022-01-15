package com.example.prototype;

public class SelectedModuleClass {
    public static String courseCode;
    public static String courseName;
    public static int credit;
    public static int occ;
    public static String activityType;
    public static String staffName;
    public static String day;
    public static String timeString;
    public static int target;
    public static int actual;

    public SelectedModuleClass(String courseCode, String courseName, int credit, int occ, String activityType, String staffName, String day, String timeString, int target, int actual){
        SelectedModuleClass.courseCode = courseCode;
        SelectedModuleClass.courseName = courseName;
        SelectedModuleClass.credit = credit;
        SelectedModuleClass.occ = occ;
        SelectedModuleClass.activityType = activityType;
        SelectedModuleClass.staffName = staffName;
        SelectedModuleClass.day = day;
        SelectedModuleClass.timeString = timeString;
        SelectedModuleClass.target = target;
        SelectedModuleClass.actual = actual;
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