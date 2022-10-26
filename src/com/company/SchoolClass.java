package com.company;

import myExceptions.MyException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolClass{
    private int classNumber;
    private List<SchoolChild> schoolChildStorage;

    public SchoolClass(int classNumber) throws MyException {
        if(classNumber<1 || classNumber > 11)
            throw new MyException("Ошибка в номере класса. ");
        this.schoolChildStorage = new ArrayList<>();
        this.classNumber = classNumber;
    }

    public int getClassNumber(){
        return this.classNumber;
    }

    public List<SchoolChild> getSchoolChildStorage(){
        return this.schoolChildStorage;
    }

    public void setClassNumber(int classNumber){
        this.classNumber = classNumber;
    }

    public void setSchoolChildStorage(List<SchoolChild> schoolChildStorage){
        this.schoolChildStorage = schoolChildStorage;
    }

    public void addChildToClass(SchoolChild schoolChild){
        this.schoolChildStorage.add(schoolChild);
    }

    public double averageValueByMarks(){
        double result = 0;
        for(SchoolChild schoolChild: this.schoolChildStorage){
            result+=schoolChild.getMark();
        }
        return result / this.schoolChildStorage.size();
    }

    public List<SchoolChild> filterSchoolChildrenBySubject(String subject){
        List<SchoolChild> a;
        a = this.getSchoolChildStorage().stream().filter(p -> p.getSubjectName().equals(subject)).collect(Collectors.toList());
        return a;
    }
}
