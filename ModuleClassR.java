package com.example.prototype;

public class ModuleClassR { // registered
    private String courseCode;
    private int occ;

    public ModuleClassR(String courseCode, int occ){
        this.courseCode = courseCode;
        this.occ = occ;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getOcc() {
        return occ;
    }
}
