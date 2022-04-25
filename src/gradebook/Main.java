package gradebook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.System.exit;

public class Main {

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

    public static ArrayList<String> getListOfCourseName() throws SQLException {
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Course> list = getList();
        for (Course c : list) {
            nameList.add(c.name);
        }
        return nameList;
    }


    public static String fill(char ch, int len) {
        StringBuilder sb = new StringBuilder(len);
        sb.append(String.valueOf(ch).repeat(Math.max(0, len)));
        return sb.toString();
    }

    public static String padString(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        return sb.append(fill(' ', len - str.length())).toString();
    }

    public static void printList() throws SQLException {
        ArrayList<Course> list = getList();
        System.out.println();
        if (list.isEmpty()) {
            System.out.println("There are no courses!");
        } else {
            System.out.println();
            int maxBoxWidth = getLengthOfLongestName();
            ArrayList<Course> courses = getList();
            String line = "+" + fill('-', maxBoxWidth + 7) + "+";
            System.out.println(line);
            for (Course course : courses) {
                System.out.printf("| %s: %s |%n", padString(course.name, maxBoxWidth), course.grade.toString());
            }
            System.out.println(line);
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
        Scanner scanner = new Scanner(System.in);
        String cName = "";
        ArrayList<String> cNameList = getListOfCourseName();
        while(!cNameList.contains(cName)) {
            System.out.print("Enter name of course: ");
            cName = scanner.nextLine();
            try {
                ArrayList<Course> list = getList();
                int pos = getCoursePosByName(cName);
                Course course = list.get(pos);
                System.out.print("Enter assignment name: ");
                String aName = scanner.nextLine();
                System.out.print("Enter weight: ");
                double weight = scanner.nextDouble();
                course.addAssignment(new Assignment(cName, aName, weight, null));
                Assignment a =  course.aList.get(getPosOfAssignmentByName(aName, cName));
                a.insert();
                System.out.println("Assignment added :)");
            } catch (InputMismatchException e) {
                System.out.println("Course does not exist, try again");
                scanner.next();
            }
        }

    }

    public static int getPosOfAssignmentByName(String aName, String cName) throws SQLException {
        ArrayList<Course> list = getList();
        int pos = 0;
        Course c = list.get(getCoursePosByName(cName));
        for (Assignment a : c.aList) {
            if (a.getName().equals(aName)) {
                pos = c.aList.indexOf(a);
            }
        }
        return pos;
    }

    public static void printMenu() {
        System.out.println();
        String[] options = {"0 - Courses", "1 - Assignments", "2 - Exit"};
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option: ");
    }

    public static void printCourseMenu() {
        System.out.println();
        String[] options = {"0 - Add course", "1 - Remove course", "2 - Show courses", "3 - Exit"};
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option: ");
    }

    public static void printAssignmentMenu() {
        System.out.println();
        String[] options = {"0 - Add assignment", "1 - Remove assignment", "2 - Show assignments", "3 - Exit"};
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option: ");
    }

    public static int getCoursePosByName(String name) throws SQLException {
        ArrayList<Course> list = getList();
        int c = 0;
        for (Course course : list) {
            if (course.name.equals(name)) {
                c = list.indexOf(course);
            }
        }
        return c;
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
                        int option1 = 0;
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
                    case 1 -> {
                        int option2 = 0;
                        while (option2 != 3) {
                            printAssignmentMenu();
                            try {
                                option2 = scnr.nextInt();
                                if (option2 == 0) {
                                    addAssignment();
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid entry, try again");
                                scnr.next();
                            }
                        }
                    }
                    case 2 -> {
                        System.out.println("\nAurevoir :)");
                        exit(0);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid entry, try again :(");
                scnr.next();
            }
        }
    }
}
