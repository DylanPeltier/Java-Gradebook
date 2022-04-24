package gradebook;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
    public final String name;
    public int grade;

    public Course(String name) throws SQLException {
        this.name = setName(name);
        this.grade  = setGrade(name);
    }

    public String setName(String name) throws SQLException {
        String courseName = "";
        SQL sql = new SQL();
        ResultSet result =  sql.sendQuery("select name from course where name = " + "'" + name + "'" + ";");
        while (result.next()) {
            courseName = result.getString("name").trim();
        }
        return courseName;
    }

    public String getName() {
        return name;
    }

    public int setGrade(String name) throws SQLException {
        int courseGrade = 0;
        SQL sql = new SQL();
        ResultSet result = sql.sendQuery("select grade from course where name = " + "'" + name + "'" + ";");
        while (result.next()) {
            courseGrade = result.getInt("grade");
        }
        return courseGrade;
    }

    public int getGrade() {
        return grade;
    }

    public boolean isPass() {
        return this.grade >= 50;
    }

    public String toString() {
        return "name: " + getName() + ", grade: " + getGrade() + ", pass: " + isPass();
    }
}
