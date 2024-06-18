package testng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TEstJDBC {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://10.250.1.84:3306/replixdb?useSSL=false&serverTimezone=UTC&autoReconnect=true";
        String username = "root";
        String password = "softlinx";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to the 10.250.1.84 new message !");
            System.out.println("jdbc:mysql://10.250.1.84:3306/replixdb?useSSL=false&serverTimezone=UTC&autoReconnect=true!99999");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
