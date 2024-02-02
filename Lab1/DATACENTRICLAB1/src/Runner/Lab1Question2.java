package Runner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import com.mysql.cj.jdbc.MysqlDataSource;

public class Lab1Question2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("INSERT A NEW SALESPERSON");
		//obtain values from scanner input
		System.out.println("\nEnter sid: ");
        String sid = scanner.nextLine();
        System.out.println("Enter First Name: ");
        String fname = scanner.nextLine();
        System.out.println("Enter Surname: ");
        String surname = scanner.nextLine();
        System.out.println("Enter dob in format YYYY-MM-DD: ");
        String dob = scanner.nextLine();
	
        //connect to database
		try { 
			MysqlDataSource mysqlDS = new MysqlDataSource();
			mysqlDS.setURL("jdbc:mysql://localhost:3306/salespersonsDB2P1?serverTimezone=UTC");
			mysqlDS.setUser("root");
			mysqlDS.setPassword("root1234");
			Connection conn = mysqlDS.getConnection();
			
			//checking if sid already existed
			String checkQuery = "SELECT * FROM salesperson_table WHERE sid = ?";
			try(PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) { //checks if SID already exists
				checkStmt.setString(1, sid);
				ResultSet rs = checkStmt.executeQuery(); 
				
				if (rs.next()) {
                    System.out.println("Error: Salesperson with sid " + sid + " already exists");
                    return;
                } else {//Inserts values into query 
                	String InsertQuery ="INSERT INTO salesperson_table (sid, fname, surname, dob) VALUES (?, ?, ?, ?)";
                	try(PreparedStatement insertStmt = conn.prepareStatement(InsertQuery)) {
                		insertStmt.setString(1, sid);
                		insertStmt.setString(2, fname);
                		insertStmt.setString(3, surname);
                		insertStmt.setDate(4, Date.valueOf(dob));
                		
                		//confirmation text for employees inserted
                		int rowsInserted = insertStmt.executeUpdate();
                        System.out.println("Salesperson inserted successfully: " + rowsInserted + " rows added.");
                        
                        //close connections
                        conn.close();
            			insertStmt.close();
            			checkStmt.close();
            			rs.close();
                	}
                }
			}	
			
		} catch (SQLException e) {
            if (e instanceof SQLDataException) {
                System.out.println("Error: Incorrect data entered: " + e.getMessage());
            } else {
                System.out.println("SQL Error: " + e.getMessage());
            }
        } catch (IllegalArgumentException e) { //catches if wrong date is entered
            System.out.println("Error: Incorrect date format entered. Please use the YYYY-MM-DD format.");
        }
	}
}


