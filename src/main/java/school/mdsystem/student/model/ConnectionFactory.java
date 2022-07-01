package school.mdsystem.student.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class ConnectionFactory {
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://sh-cdb-70hmlmby.sql.tencentcdb.com:58587/sms";
    public static final String USER = "root";
    public static final String PASSWORD = "py20021023";
    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    // private constructor
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        return instance.createConnection();
    }

}
