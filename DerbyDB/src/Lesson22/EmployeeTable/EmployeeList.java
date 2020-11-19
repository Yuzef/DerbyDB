package Lesson22.EmployeeTable;

import java.sql.*;

public class EmployeeList {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// Load the JDBC driver
			// This can be skipped for Derby, but derbyclient.jar has to be in the CLASSPATH
			// Class.forName("org.apache.derby.jdbc.ClientDriver");
			
			conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Lesson22");
			// DriverManager - old way to get connection,
			// better to use DataSource (to make pool of connections)
			
			// Build a SQL String
			String sqlQuery = "INSERT INTO Employee " + "values(12345, 'Tito', 'Programmer')";
			
			// Transactional Updates
			try {
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sqlQuery);
				stmt.addBatch();
				stmt.executeBatch();
				conn.commit();// Transaction succeeded
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				conn.rollback(); // if something is wrong in commit section
				e.printStackTrace();
			}
			
			sqlQuery = "SELECT * from Employee";
			// Create a statement object
			stmt = conn.prepareStatement(sqlQuery);
			
			// Execute SQL and get obtain the ResultSet object
			rs = stmt.executeQuery();
			
			// Process the ResultSet rs - print Employees
			while (rs.next()) {
				int empNo = rs.getInt("EMPNO");
				String eName = rs.getString("ENAME");
				String job = rs.getString("JOB_TITLE");
				System.out.println("" + empNo + ", " + eName + ", " + job);
			}
			
		} catch (SQLException se) {
			System.out.println("SQL exception: " + se.getMessage() + 
					           " code: " + se.getErrorCode());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			// clean up system resources
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
