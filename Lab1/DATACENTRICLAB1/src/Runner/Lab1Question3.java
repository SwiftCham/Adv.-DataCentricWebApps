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
import java.util.*;

public class Lab1Question3 {
	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<>();
		//connect to database
		try { 
			MysqlDataSource mysqlDS = new MysqlDataSource();
			mysqlDS.setURL("jdbc:mysql://localhost:3306/employee_kin?serverTimezone=UTC");
			mysqlDS.setUser("root");
			mysqlDS.setPassword("root1234");
			Connection conn = mysqlDS.getConnection();
			
			String query = "SELECT e.eid, e.ename, n.NOK_Name, s.salary FROM employee_table e "
					+ "INNER JOIN salary s ON e.eid = s.eid "
					+ "INNER JOIN next_of_kin_table n ON e.NextOfKin = n.NOK_ID";
			
			try(PreparedStatement Stmt = conn.prepareStatement(query)) {
				ResultSet rs = Stmt.executeQuery();
				while(rs.next()) {
					String eid = rs.getString("eid");
					String ename = rs.getString("ename");
					String NOK_Name = rs.getString("NOK_Name");
					double salary = rs.getDouble("salary");
					employees.add(new Employee(eid, ename, NOK_Name, salary));
				}
			}
				
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		//print out details for every employee that matches the criteria
		for (Employee employee : employees) {
            System.out.println(employee);
        }
	}
}

//class for employee object
class Employee {
    private String id;
    private String name;
    private String nextOfKinName;
    private double salary;

    public Employee(String id, String name, String nextOfKinName, double salary) {
        this.id = id;
        this.name = name;
        this.nextOfKinName = nextOfKinName;
        this.salary = salary;
    }

    //returns employee object as string text
    @Override
    public String toString() {
        return " | Employee ID: " + id + " | Name: " + name + " | Next of Kin: " + nextOfKinName + " | Salary: " + salary + " | ";
    }
}
