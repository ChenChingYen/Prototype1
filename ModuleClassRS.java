package com.example.prototype;

import javafx.scene.control.Button;

public class ModuleClassRS {

    private String courseCode;
    private int occ;
    private String courseName;
    private int credit;
    private int target;
    private int actual;

    public ModuleClassRS(String courseCode, int occ, String courseName, int credit, int target, int actual){
        this.courseCode = courseCode;
        this.occ = occ;
        this.courseName = courseName;
        this.credit = credit;
        this.target = target;
        this.actual = actual;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getOcc() {
        return occ;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public int getTarget() {
        return target;
    }

    public int getActual() {
        return actual;
    }
}
