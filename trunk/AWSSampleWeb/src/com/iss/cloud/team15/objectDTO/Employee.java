package com.iss.cloud.team15.objectDTO;

public class Employee {
	private int empNo;
	private String empName;
	private int empAge;
	private String gender;
	private String job;
	private String hireDate;
	private int daysOfWork;
	private float salary;	
	
	public void setEmpNo(int empNo){
		this.empNo = empNo;
	}
	public int getEmpNo(){
		return this.empNo;
	}
	
	public void setEmpName(String empName){
		this.empName = empName;
	}
	
	public String getEmpName(){
		return this.empName;
	}
	
	public void setEmpAge(int age){
		this.empAge = age;
	}
	public int getEmpAge(){
		return this.empAge;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	public String getGender(){
		return this.gender;
	}
	
	public void setJob(String job){
		this.job = job;
	}
	public String getJob(){
		return this.job;
	}
	
	public void setHireDate(String hireDate){
		this.hireDate = hireDate;
	}
	public String getHireDate(){
		return this.hireDate;
	}
	
	public void setDaysOfWork(int days){
		this.daysOfWork = days;
	}
	public int getDaysOfWork(){
		return this.daysOfWork;
	}
	
	public void setSalary(float salary){
		this.salary = salary;
	}
	public float getSalary(){
		return this.salary;
	}
}


