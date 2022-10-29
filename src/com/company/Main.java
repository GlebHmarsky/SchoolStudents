package com.company;

import myExceptions.MyException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<SchoolClass> readData(String schoolDataFilePath) throws IOException, MyException {
        List<SchoolClass> classes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(schoolDataFilePath));
        int iteration = 0;
        String line;
        while ((line = br.readLine()) != null) {
            try {
                boolean classExist = false;
                iteration++;
                String[] values = line.split("\\s+");
                SchoolChild schoolChild = new SchoolChild(values[0] + " " + values[1], values[3], Integer.parseInt(values[4]));
                for(SchoolClass schoolClass: classes){
                    if(schoolClass.getClassNumber() == Integer.parseInt(values[2])) {
                        schoolClass.addChildToClass(schoolChild);
                        classExist = true;
                        break;
                    }
                }
                if(!classExist) {
                    SchoolClass schoolClass = new SchoolClass(Integer.parseInt(values[2]));
                    schoolClass.addChildToClass(schoolChild);
                    classes.add(schoolClass);
                }
            }  catch (NumberFormatException e) {
                System.out.println("Ошибка в формате данных файла \"" + schoolDataFilePath + "\". Запись " + (iteration - 1));
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                System.out.println("Недостаточно данных в файле \"" + schoolDataFilePath + "\". Запись " + (iteration - 1));
            }
        }
        br.close();
        if (classes.isEmpty())
            throw new MyException("В файле \"" + schoolDataFilePath + "\" не найдено информации об учениках");
        return classes;
    }

    public static void averageGradePerClass(List<SchoolClass> classes){
        for(SchoolClass schoolClass: classes){
            System.out.println("Класс № " + schoolClass.getClassNumber() + " Ср. арифметическое: " +
                    schoolClass.averageValueByMarks());
        }
        System.out.println("__________________");
    }

    public static void studentsSortedPerClass(List<SchoolClass> classes){
        for(SchoolClass schoolClass: classes){
            System.out.println("Класс № " + schoolClass.getClassNumber());
            schoolClass.getSchoolChildStorage().stream().sorted().forEach(System.out::println);
            System.out.println();
        }
        System.out.println("__________________");
    }

    private static List<SchoolChild> filterBySubject(String subject, SchoolClass schoolClass){
        List<SchoolChild> filteredList = schoolClass.filterSchoolChildrenBySubject(subject);
        if(filteredList.isEmpty())
            System.out.println("В классе нет такого предмета.");
        return filteredList;
    }

    public static void infoStudentsPerClass(Scanner sc, List<SchoolClass> classes){
        String subject;
        while (true) {
            System.out.print("Введите название предмета или введите 'назад' чтобы вернуться назад\n" +
                    ">>>\n");
            subject = sc.next();
            if (subject.toLowerCase().equals("назад"))
                break;
            for(SchoolClass schoolClass: classes){
                System.out.println("Класс № " + schoolClass.getClassNumber());
                List<SchoolChild> filteredList = filterBySubject(subject,schoolClass);
                filteredList.stream().sorted().forEach(System.out::println);
                System.out.println();
            }
            System.out.println("__________________");
        }
    }

    public static void main(String[] args) {
        try {
            String schoolDataFilePath = "data/data_school.txt";
            List<SchoolClass> classes = readData(schoolDataFilePath);
            Scanner sc = new Scanner(System.in);
            int action;
            while (true) {
                System.out.print("Выберите действие, которое хотите выполнить:\n" +
                        "1.Вывести для каждого класса среднее арифметическое по оценкам\n" +
                        "2.Вывести список школьников, упорядоченный по оценкам, для каждого класса\n" +
                        "3.Вывести информацию о школьниках и их оценках по введенному предмету\n" +
                        "4.Выход из программы\n>>>");
                action = sc.nextInt();
                switch (action) {
                    case 1:
                        averageGradePerClass(classes);
                        break;
                    case 2:
                        studentsSortedPerClass(classes);
                        break;
                    case 3:
                        infoStudentsPerClass(sc, classes);
                        break;
                    case 4:
                        return;
                }
            }
        } catch (MyException | FileNotFoundException e) {
            System.out.println(e.getMessage() + ". Завершение работы.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
