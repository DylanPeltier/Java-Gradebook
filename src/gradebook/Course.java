package gradebook;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {

    public final String name;
    public double grade;
    SQL sql = new SQL();

    public Course(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    public void insert() {
        sql.sendUpdate("INSERT INTO courses " + "VALUES ('" + name + "', " + grade + ");");
    }

    public void updateGrade(double grade) {
        sql.sendUpdate("UPDATE courses SET grade = " + grade + "WHERE name = " + "'" + this.name + "';");
    }
}
