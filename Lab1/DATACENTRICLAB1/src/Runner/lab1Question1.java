package Runner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class lab1Question1 {
	public static void main(String[] args) {
		try {
			System.out.println("Hello world!");
			MysqlDataSource mysqlDS = new MysqlDataSource();
			mysqlDS.setURL("jdbc:mysql://localhost:3306/salespersondb?serverTimezone=UTC");
			mysqlDS.setUser("root");
			mysqlDS.setPassword("root1234");
			Connection conn = mysqlDS.getConnection();
			
			//part2 create scanner
			Scanner com = new Scanner(System.in);
			System.out.println("Enter commission to be found");
			double input = com.nextDouble();
			String query = "SELECT * FROM salesperson_table WHERE commission <= ?";
			PreparedStatement myStmt = conn.prepareStatement(query);
			myStmt.setDouble(1, input);
			
			
			ResultSet rs = myStmt.executeQuery();
			while(rs.next()) {
				String sid = rs.getString("sid");
				String fname = rs.getString("fname");
				String surname = rs.getString("surname");
				String dob = rs.getString("dob");
				String city = rs.getString("city");
				String commission = rs.getString("commission");
				System.out.println(sid +" | " + fname +" | " +surname +" | " +dob +" | " +city +" | " +commission);
			}
			conn.close();
			myStmt.close();
			rs.close();
			
		} catch (SQLException se){
			System.out.println(se.getMessage());
		}
	}
}


