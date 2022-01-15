package com.example.prototype;

public class StudentClassSR {
    private String siswamail;
    private String matricNumber;
    private String name;
    private String programme;
    private String gender;
    private String nationality;
    private String icNumber;
    private int muet;
    private int credits;
    private String status;

    public StudentClassSR(String siswamail, String matricNumber, String name, String programme, String gender, String nationality, String icNumber, int muet, int credits, String status){
        this.siswamail = siswamail;
        this.matricNumber = matricNumber;
        this.name = name;
        this.programme = programme;
        this.gender = gender;
        this.nationality = nationality;
        this.icNumber = icNumber;
        this.muet = muet;
        this.credits = credits;
        this.status = status;
    }

    public String getSiswamail(){
        return this.siswamail;
    }

    public String getMatricNumber(){
        return this.matricNumber;
    }

    public String getName(){
        return this.name;
    }

    public String getProgramme(){
        return this.programme;
    }

    public String getGender(){
        return this.gender;
    }

    public String getNationality(){
        return this.nationality;
    }

    public int getMuet(){
        return this.muet;
    }

    public String getIcNumber(){
        return this.icNumber;
    }

    public int getCredits(){
        return this.credits;
    }

    public String getStatus(){
        return this.status;
    }
}
