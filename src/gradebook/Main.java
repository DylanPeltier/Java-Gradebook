package gradebook;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Course c = new Course("test1", 60);
        Assignment a = new Assignment("System Programming", "test06", 0.01, 95);
    }

}
