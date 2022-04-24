package gradebook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

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

    public static void main(String[] args) throws SQLException {
        ArrayList<Course> cList;
        cList = getList();
        cList.get(findPosOfCourse("System Programming", cList)).getAssignmentByName("Final Exam").updateGrade(32);
    }
}
