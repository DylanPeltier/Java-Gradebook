package gradebook;

import java.util.ArrayList;

public class Course {

    public final String name;
    public Double grade;
    SQL sql = new SQL();
    ArrayList<Assignment> aList = new ArrayList<>();

    public Course(String name, Double grade) {
        this.name = name;
        this.grade = grade;
    }

    public void insert() {
        sql.sendUpdate("INSERT INTO courses " + "VALUES ('" + name + "', " + grade + ");");
    }

    public void updateGrade(double grade) {
        sql.sendUpdate("UPDATE courses SET grade = " + grade + "WHERE name = " + "'" + this.name + "';");
    }

    public void addAssignment(Assignment a) {
        aList.add(a);
    }

    public String getName() {
        return this.name;
    }

    public Assignment getAssignmentByName(String name) {
        Assignment assignment = null;
        for (Assignment a : aList) {
            if (a.getName().equals(name)) {
                assignment = a;
            }
        }
        return assignment;
    }
}
