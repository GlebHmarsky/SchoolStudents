package com.company;

import myExceptions.MyException;

public class SchoolChild implements Comparable<SchoolChild>{
    private String fullName;
    private String subjectName;
    private int mark;

    public SchoolChild(String fullName, String subjectName, int mark) throws MyException {
        if(mark<2 || mark > 5)
            throw new MyException("Ошибка в оценке. ");
        this.fullName=fullName;
        this.subjectName=subjectName;
        this.mark=mark;
    }

    public String getFullName(){
        return this.fullName;
    }

    public String getSubjectName(){
        return this.subjectName;
    }

    public int getMark(){
        return this.mark;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setSubjectName(String subjectName){
        this.subjectName=subjectName;
    }

    public void setMark(int mark){
        this.mark=mark;
    }

    @Override
    public int compareTo(SchoolChild otherSchoolChild) {
        return this.mark - otherSchoolChild.getMark();
    }

    @Override
    public String toString(){
        return "Имя: " + getFullName() + ", Предмет: " + getSubjectName() + ", Оценка: " + getMark();
    }
}
