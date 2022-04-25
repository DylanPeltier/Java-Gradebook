package gradebook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Course {

    public final String name;
    public Double grade;
    SQL sql = new SQL();
    ArrayList<Assignment> aList;

    public Course(String name, Double grade) throws SQLException {
        this.name = name;
        this.grade = grade;
        this.aList = getAssignmentList();
    }

    public void insert() {
        sql.sendUpdate("INSERT INTO courses " + "VALUES ('" + name + "', " + grade + ");");
    }

    public void updateGrade(double grade) {
        sql.sendUpdate("UPDATE courses SET grade = " + grade + "WHERE name = " + "'" + this.name + "';");
    }

    public void addAssignment(Assignment a) {
        this.aList.add(a);
    }

    public String getName() {
        return this.name;
    }
    
    public int getPos(String name) {
        int pos = 0;
        for (Assignment a : aList) {
            if (a.getName().equals(name)) {
                pos =  aList.indexOf(a);
            }
        }
        return pos;
    }

    public Assignment getAssignmentByName(String name) {
        return aList.get(getPos(name));
    }
        
    public ArrayList<Assignment> getAssignmentList() throws SQLException {
        SQL sql = new SQL();
        ResultSet result;
        ArrayList<Assignment> list = new ArrayList<>();
        result = sql.sendQuery("SELECT * FROM assignments WHERE class_name = '" + this.name + "';");

        while (result.next()) {
            list.add(new Assignment(result.getString("class_name"), result.getString("name"), result.getDouble("weight"), result.getDouble("grade")));
        }
        return list;
    }

    public double getGrade() {
        return this.grade;
    }
}
