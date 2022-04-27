package gradebook;


public class Assignment {

    private final String className;
    public final String name;
    private final Double weight;
    public Double grade;
    SQL sql = new SQL();

    public Assignment(String className, String name, Double weight, Double grade) {
        this.className = className;
        this.name = name;
        this.weight = weight;
        this.grade = grade;
    }

    public void insert() {
        sql.sendUpdate("INSERT INTO assignments VALUES ('" + className + "', '" + name + "', " + weight + ", " + grade + ");");
    }

    public void delete() {
        sql.sendUpdate("DELETE FROM assignments WHERE name = " + "'" + this.name + "';");
    }

    public void updateGrade(double grade) {
        sql.sendUpdate("UPDATE assignments SET grade = " + grade + "WHERE name = " + "'" + this.name + "';");
    }

    public String getName() {
        return this.name;
    }

    public double getGrade() {
        return this.grade;
    }

    public void printInfo() {
        System.out.println(this.name + ": grade = " + this.grade + ", weight = " + this.weight);
    }

    public String getClassName() {
        return this.className;
    }

}
