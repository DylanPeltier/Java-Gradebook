package gradebook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.System.exit;

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

    public static int getLengthOfLongestName() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        for (Course course : getList()) {
            list.add(course.name);
        }
        String longest = Collections.max(list, Comparator.comparing(String::length));
        return longest.length();
    }

    public static int getLength(String str) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        int len = 0;
        for (Course course : getList()) {
            if (course.name.equals(str)) {
                len = course.name.length();
            }
        }
        return len;
    }

    public static void printSpaces(String str) throws SQLException {
        String s = " ".repeat(Math.max(0, ((getLengthOfLongestName() - getLength(str)))));
        System.out.print(s);
    }

    public static void printHyphens() throws SQLException {
        String s = "-".repeat(Math.max(0, ((getLengthOfLongestName() + 7))));
        System.out.print(s);
    }

    public static void printList() throws SQLException {
        ArrayList<Course> cList = getList();
        if (cList.isEmpty()) {
            System.out.println("No courses!");
        } else {
            System.out.println();
            System.out.print("+");
            printHyphens();
            System.out.print("+");
            System.out.println();
            for (Course c : cList) {
                System.out.print("| " + c.getName() + ": " + c.getGrade());
                printSpaces(c.getName());
                System.out.println(" |");
            }
            System.out.print("+");
            printHyphens();
            System.out.print("+");
            System.out.println();
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
        System.out.println("\nCourse added :)\n");
    }

    public static void removeCourse() throws SQLException {
        ArrayList<Course> list = getList();
        System.out.println();
        System.out.print("Enter course name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Course course = list.get(findPosOfCourse(name, list));
        list.remove(course);
        course.delete();
        System.out.println();
        System.out.println("Course deleted!");
        System.out.println();
    }

    public static void addAssignment() throws SQLException {
        ArrayList<Course> list = getList();
        System.out.println();
        printList();
        System.out.print("\nChoose a course to add an assignment to: ");
        Scanner scanner = new Scanner(System.in);
        int courseOption = scanner.nextInt();
        Course course = list.get(courseOption);
        System.out.print("\nEnter name of assignment: ");
        String assignmentName = scanner.next();
        System.out.print("\nEnter weight of assignment: ");
        double weight = scanner.nextDouble();
        course.aList.add(new Assignment(course.name, assignmentName, weight, null));
        System.out.println("\nAssignment added :)\n");
    }

    public static void printMenu() {
        System.out.println();
        String[] options = {"0 - Courses", "1 - Assignments", "2 - Exit"};
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option: ");
    }

    private static void printCourseMenu() {
        System.out.println();
        String[] options = {"0 - Add course", "1 - Remove course", "2 - Show courses", "3 - Exit"};
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option: ");
    }


    public static void main(String[] args) throws SQLException {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome to my gradebook!");
        System.out.println();
        int option = 0;
        while (option != 2) {
            printMenu();
            try {
                option = scnr.nextInt();
                switch (option) {
                    case 0 -> {
                        int option1 = -1;
                        while (option1 != 3) {
                            printCourseMenu();
                            try {
                                option1 = scnr.nextInt();
                                switch (option1) {
                                    case 0 -> {
                                        ArrayList<Course> list = getList();
                                        addCourse();
                                    }
                                    case 1 -> {
                                        ArrayList<Course> list = getList();
                                        removeCourse();
                                    }
                                    case 2 -> {
                                        ArrayList<Course> list = getList();
                                        printList();
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid entry, try again");
                                scnr.next();
                            } catch (Exception e) {
                                System.out.println("Unexpected error, try again");
                                scnr.next();
                            }
                        }
                    }
                    case 1 -> addAssignment();
                    case 2 -> {
                        System.out.println("\nAurevoir :)");
                        exit(0);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid entry, try again :(");
                scnr.next();
            } catch (Exception e) {
                System.out.println("Unexpected error, please try again :(");
                scnr.next();
            }
        }
    }
}
