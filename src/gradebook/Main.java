package gradebook;

import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static ArrayList<Course> createCourseList() {
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

    public static void main(String[] args) {
       ArrayList<Course> cList;
       cList = createCourseList();
       cList.get(findPosOfCourse("System Programming", cList)).addAssignment(new Assignment(cList.get(findPosOfCourse("System Programming", cList)).name, "Final Exam", 0.5, 0));
//       cList.get(findPosOfCourse("Data Structures & Algorithms", cList)).addAssignment(new Assignment(cList.get(findPosOfCourse("Data Structures & Algorithms", cList)).name, "Final Exam", 0.5, 95.5));
//       cList.get(findPosOfCourse("Introduction to Astronomy II", cList)).addAssignment(new Assignment(cList.get(findPosOfCourse("Introduction to Astronomy II", cList)).name, "Final Exam", 0.5, 95.5));
//       cList.get(findPosOfCourse("Object Oriented Programming with Java", cList)).addAssignment(new Assignment(cList.get(findPosOfCourse("Object Oriented Programming with Java", cList)).name, "Final Exam", 0.5, 95.5));
//       cList.get(findPosOfCourse("Mathematical Foundations", cList)).addAssignment(new Assignment(cList.get(findPosOfCourse("Mathematical Foundations", cList)).name, "Final Exam", 0.5, 95.5));
//       cList.get(findPosOfCourse("Data Structures & Algorithms", cList)).updateGrade(50);
        cList.get(findPosOfCourse("System Programming", cList)).getAssignmentByName("Final Exam").updateGrade(69.420);
    }

}
