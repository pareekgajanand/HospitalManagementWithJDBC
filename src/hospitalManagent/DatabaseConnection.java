package hospitalManagent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                System.out.println("Connected to MySQL database!");
            } catch (SQLException e) {
//                System.err.println("Failed to connect to MySQL database!");
                e.printStackTrace();
            }
        }
        return conn;
    }
}
