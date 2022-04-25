package gradebook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static ArrayList<Course> createCourseList() throws SQLException {
        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(0, new Course("System Programming", null));
        courseList.add(1, new Course("Data Structures & Algorithms", null));
        courseList.add(2, new Course("Introduction to Astronomy II", null));
        courseList.add(3, new Course("Object Oriented Programming with Java", null));
        courseList.add(4, new Course("Mathematical Foundations", null));
        for (Course course : courseList) {
            course.insert();
        }
        return courseList;
    }

    public static int findPosOfCourse(String name, ArrayList<Course> list) {
        int pos = 0;
        for (Course c : list) {
            if (c.getName().equals(name)) {
                pos = list.indexOf(c);
            }
        }
        return pos;
    }

    public static ArrayList<Course> getList() throws SQLException {
        SQL sql = new SQL();
        ResultSet result;
        ArrayList<Course> list = new ArrayList<>();
        result = sql.sendQuery("SELECT * FROM courses");

        while (result.next()) {
            list.add(new Course(result.getString("name"), result.getDouble("grade")));
        }
        return list;
    }

    public static void printList(ArrayList<Course> list) {
        for (Course c : list) {
            System.out.println(list.indexOf(c) + " | " + c.getName() + ": " + c.getGrade());
        }
    }

    public static void printAssignmentList(ArrayList<Assignment> list) {
        for (Assignment a : list) {
            System.out.println(list.indexOf(a) + " | " + a.getName() + ": " + a.getGrade());
        }
    }

    public static void addCourse() throws SQLException {
        ArrayList<Course> list = getList();
        System.out.print("\nEnter course name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Course course = new Course(name, null);
        list.add(course);
        course.insert();
        System.out.println("\nCourse added :)");
    }

    public static void addAssignment() throws SQLException {
        ArrayList<Course> list = getList();
        System.out.println();
        printList(list);
        System.out.print("\nChoose a course to add an assignment to: ");
        Scanner scanner = new Scanner(System.in);
        int courseOption = scanner.nextInt();
        Course course = list.get(courseOption);
        System.out.print("\nEnter name of assignment: ");
        String assignmentName = scanner.next();
        System.out.print("\nEnter weight of assignment: ");
        double weight = scanner.nextDouble();
        course.aList.add(new Assignment(course.name, assignmentName, weight, null));
        System.out.println("\nAssignment added :)");
    }

    public static void main(String[] args) throws SQLException {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome to my gradebook!");
        System.out.println();
        System.out.println("Menu options:");
        System.out.println("0: Add course\n1: Add assignment\n2: Show courses\n3: Show assignments\n");
        System.out.print("Enter option number: ");
        int decision = scnr.nextInt();
        switch (decision) {
            case 0 -> addCourse();
            case 1 -> addAssignment();
        }
    }
}
