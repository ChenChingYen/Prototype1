package com.example.prototype;

public class StaffClass {

    private static String UMmail;
    private static String staffID;
    private static String name;
    private static String gender;
    private static String nationality;
    private static String ICNumber;

    public StaffClass(){}

    public StaffClass(String UMmail, String staffID, String name, String gender, String nationality, String ICNumber){
        StaffClass.UMmail = UMmail;
        StaffClass.staffID = staffID;
        StaffClass.name = name;
        StaffClass.gender = gender;
        StaffClass.nationality = nationality;
        StaffClass.ICNumber = ICNumber;
    }

    public static String getUMmail(){
        return UMmail;
    }
    public static String getStaffID(){
        return staffID;
    }
    public static String getName(){
        return name;
    }
    public static String getGender(){
        return gender;
    }
    public static String getNationality(){
        return nationality;
    }
    public static String getICNumber(){
        return ICNumber;
    }
}
