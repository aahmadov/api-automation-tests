package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Java MySQL SELECT statement example.
 * Demonstrates the use of a SQL SELECT statement against a
 * MySQL database, called from a Java program.
 * <p>
 * Created by Abbas Aydinoglu,
 */
public class DataBaseUtility {

    private static Connection connection;
    private static Connection connection2;
    private static Connection connection3;
    private static Connection connection84;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void openConnection() throws SQLException {
        if (connection84 != null && connection84.isValid(20)) {
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Ooops error!");
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(ConfigReader.getProperty("mysql.url"),
                ConfigReader.getProperty("replixdb.username"),
                ConfigReader.getProperty("replixdb.password"));

        connection2 = DriverManager.getConnection(ConfigReader.getProperty("mysql.url_IgnoreBusyFeature"),
                ConfigReader.getProperty("replixdb.username"),
                ConfigReader.getProperty("replixdb.password"));


        connection3 = DriverManager.getConnection(ConfigReader.getProperty("mysql.url_IgnoreBusyFeatureNEw"),
                ConfigReader.getProperty("replixdb.username"),
                ConfigReader.getProperty("replixdb.password"));

        String jdbcUrl = "jdbc:mysql://10.250.1.84:3306/replixdb?useSSL=false&serverTimezone=UTC&autoReconnect=true";
        String username = "root";
        String password = "softlinx";
        connection84 = DriverManager.getConnection(jdbcUrl,username,password);

    }

    public static List<Map<String, Object>> executeSQLQuery(String query) throws SQLException {

        openConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map<String, Object>> table = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int column = 1; column <= columnCount; column++) {
                System.out.print(metaData.getColumnName(column) + ":");
                map.put(metaData.getColumnName(column), resultSet.getObject(column));
                System.out.println(map.put(metaData.getColumnName(column), resultSet.getObject(column)));
            }

            System.out.print("\n");

            table.add(map);
        }
        closeConnection();
        return table;
    }
    public static List<Map<String, Object>> executeSQLQueryRecvD(String query) throws SQLException {

        openConnection();
        statement = connection3.createStatement();
        resultSet = statement.executeQuery(query);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map<String, Object>> table = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int column = 1; column <= columnCount; column++) {
                System.out.print(metaData.getColumnName(column) + ":");
                map.put(metaData.getColumnName(column), resultSet.getObject(column));
                System.out.println(map.put(metaData.getColumnName(column), resultSet.getObject(column)));
            }

            System.out.print("\n");

            table.add(map);
        }
        closeConnection();
        return table;
    }

    public static List<Map<String, Object>> executeSQLQueryAuto1(String query) throws SQLException {

        openConnection();
        statement = connection2.createStatement();
        resultSet = statement.executeQuery(query);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map<String, Object>> table = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int column = 1; column <= columnCount; column++) {
                System.out.print(metaData.getColumnName(column) + ":");
                map.put(metaData.getColumnName(column), resultSet.getObject(column));
                System.out.println(map.put(metaData.getColumnName(column), resultSet.getObject(column)));
            }

            System.out.print("\n");

            table.add(map);
        }
        closeConnection();
        return table;
    }


    public static List<Map<String, Object>> executeSQLQueryAuto184(String query) throws SQLException {

        openConnection();
        statement = connection84.createStatement();
        resultSet = statement.executeQuery(query);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map<String, Object>> table = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int column = 1; column <= columnCount; column++) {
                System.out.print(metaData.getColumnName(column) + ":");
                map.put(metaData.getColumnName(column), resultSet.getObject(column));
                System.out.println(map.put(metaData.getColumnName(column), resultSet.getObject(column)));
            }

            System.out.print("\n");

            table.add(map);
        }
        closeConnection();
        return table;
    }

    public static void executeSQLUpdate(final String query) throws SQLException {
        openConnection();
        statement = connection.createStatement();
        int noOfLines = statement.executeUpdate(query);
        closeConnection();
    }

    public static void executeSQLUpdate2(final String query) throws SQLException {
        openConnection();
        statement = connection2.createStatement();
        int noOfLines = statement.executeUpdate(query);
        closeConnection();
    }
    public static void executeSQLUpdateRecvD(final String query) throws SQLException {
        openConnection();
        statement = connection3.createStatement();
        int noOfLines = statement.executeUpdate(query);
        closeConnection();
    }
    public static void executeSQLUpdateRecvD84(final String query) throws SQLException {
        openConnection();
        statement = connection84.createStatement();
        int noOfLines = statement.executeUpdate(query);
        closeConnection();
//        return query;
    }

    public static void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
