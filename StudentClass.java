package com.example.prototype;

public class StudentClass {

    private static String siswamail;
    private static String matricNumber;
    private static String name;
    private static String programme;
    private static String gender;
    private static String nationality;
    private static String ICNumber;
    private static int muet;
    private static int credit;
    private static int status;

    public StudentClass(){}

    public StudentClass(String siswamail, String matricNumber, String name, String programme, String gender, String nationality, String ICNumber, int muet, int credit, int status){
        StudentClass.siswamail = siswamail;
        StudentClass.matricNumber = matricNumber;
        StudentClass.name = name;
        StudentClass.programme = programme;
        StudentClass.gender = gender;
        StudentClass.nationality = nationality;
        StudentClass.ICNumber = ICNumber;
        StudentClass.muet = muet;
        StudentClass.credit = credit;
        StudentClass.status = status;
    }

    public static String getSiswamail(){
        return siswamail;
    }

    public static String getMatricNumber(){
        return matricNumber;
    }

    public static String getName(){
        return name;
    }

    public static String getProgramme(){
        return programme;
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

    public static int getMuet(){
        return muet;
    }

    public static int getCredit(){
        return credit;
    }

    public static int getStatus(){
        return status;
    }

    public static void setCredit(int credit){
        StudentClass.credit = credit;
    }

    public static void setStatus(int status){
        StudentClass.status = status;
    }

}
