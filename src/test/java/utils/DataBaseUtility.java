package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A Java MySQL SELECT statement example.
 * Demonstrates the use of a SQL SELECT statement against a
 * MySQL database, called from a Java program.
 * 
 * Created by Abbas Ahamdov,
 */

public class DataBaseUtility {
	
	
	private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
   
    public static void openConnection() throws SQLException {
        try {
      
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	System.out.println("Ooops error!");
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(ConfigReader.getProperty("mysql.url"),
                ConfigReader.getProperty("replixdb.username"),
                ConfigReader.getProperty("replixdb.password"));
       
    }
    public static List<Map<String, Object>> executeSQLQuery(String query) throws SQLException {
    	
   
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
       
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map<String, Object>> table = new ArrayList<>();
        while(resultSet.next()){
            Map<String, Object> map = new HashMap<>();
            for(int column =1; column<=columnCount; column++){
            	 System.out.print(metaData.getColumnName(column) + ":");
                map.put(metaData.getColumnName(column), resultSet.getObject(column));
                System.out.println(map.put( metaData.getColumnName(column), resultSet.getObject(column)));
            }
            
                System.out.printf("\n");
            
            table.add(map);
        }
        return table;
    }
    public static void closeConnection(){
        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    public static void main(String[] args) throws SQLException{
    	
    	openConnection();
    	//executeSQLQuery("SELECT JobStatus,Pages,SentPages FROM acme.sendstatus where JobID=774;");
    	//executeSQLQuery("SELECT JobID,Pages ,FaxNumber,Error FROM acme.sendstatus where JobStatus='send Failed'");
    	executeSQLQuery("SELECT JobID,Pages,TransmiStationID,Error FROM acme.recvstatus where JobStatus='Recv Fail'");
    	//executeSQLQuery("select JobStatus,FaxuserID,Pages,TransmiStationID from acme.recvstatus where JobID = 71 ;");
    	//executeSQLQuery("SELECT JobStatus,Pages,SentPages FROM acme.sendstatus where JobID=774;");
    	executeSQLQuery("select * from acme.recvstatus where TransmiStationID = 'Test45';");
    	closeConnection();
    }
    
    
}
