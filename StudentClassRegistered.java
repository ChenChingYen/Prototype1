package com.example.prototype;

public class StudentClassRegistered {
    private String matricNumber;
    private String name;

    public StudentClassRegistered(String matricNumber, String name){
        this.matricNumber = matricNumber;
        this.name = name;
    }

    public String getMatricNumber(){
        return this.matricNumber;
    }

    public String getName(){
        return this.name;
    }
}
