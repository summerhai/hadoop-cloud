package com.iss.cloud.team15.dataLayer;

import java.sql.*;

import com.iss.cloud.team15.objectDTO.Employee;


public class EmpDAO {
	private String dbUrl = "jdbc:MySql://localhost:3306/mydb";
	private String dbClass = "com.mysql.jdbc.Driver";
	private String query = null;
	private Connection conn;
	
	private void connectDB(){
		try{
			Class.forName(dbClass);
			conn = DriverManager.getConnection(dbUrl);			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public Employee getEmployee(int empNo){
		connectDB();
		Employee e = new Employee();
		query = "select * from Employee where EmpNo = " + empNo;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()){
				e.setEmpNo(rs.getInt("EmpNo"));
				e.setEmpName(rs.getString("EmpName"));
				e.setEmpAge(rs.getInt("Age"));
				e.setGender(rs.getString("Gender"));
				e.setJob(rs.getString("Job"));
				e.setHireDate(rs.getString("HireDate"));
				e.setDaysOfWork(rs.getInt("DaysOfWork"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
		return e;
	}
	
	public Employee getEmployee(String empName){
		connectDB();
		Employee e = new Employee();
		query = "select * from Employee where EmpName = " + empName;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()){
				e.setEmpNo(rs.getInt("EmpNo"));
				e.setEmpName(rs.getString("EmpName"));
				e.setEmpAge(rs.getInt("Age"));
				e.setGender(rs.getString("Gender"));
				e.setJob(rs.getString("Job"));
				e.setHireDate(rs.getString("HireDate"));
				e.setDaysOfWork(rs.getInt("DaysOfWork"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
		return e;
	}
}
