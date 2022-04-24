package gradebook;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Course c = new Course("System Programming");
        System.out.println(c);
    }

}
