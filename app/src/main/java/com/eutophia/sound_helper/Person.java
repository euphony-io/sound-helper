package com.eutophia.sound_helper;

public class Person {
    private String name, tel, birthOfDate, diseaseName;

    public void setName(String name){
        this.name = name;
    }
    public void setTel(String tel){
        this.tel = tel;
    }
    public void setBirthOfDate(String birthOfDate){
        this.birthOfDate = birthOfDate;
    }
    public void setDiseaseName(String diseaseName){
        this.diseaseName = diseaseName;
    }

    public String getName(){
        return this.name;
    }
    public String getTel(){
        return this.tel;
    }
    public String getBirthOfDate(){
        return this.birthOfDate;
    }
    public String getDiseaseName(){
        return this.diseaseName;
    }
}
