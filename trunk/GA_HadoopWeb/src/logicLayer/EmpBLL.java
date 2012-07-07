package logicLayer;

import dataLayer.EmpDAO;
import objectDTO.Employee;

public class EmpBLL {
	private float salary;
	private final static int RATE = 20;
	
	public Employee getEmployee(int empNo){
		EmpDAO empDAO = new EmpDAO();
		Employee e = new Employee();
		e = empDAO.getEmployee(empNo);
		salary = e.getDaysOfWork() * RATE;
		e.setSalary(salary);
		
		return e;
	}
	
	public Employee getEmployee(String empName){
		EmpDAO empDAO = new EmpDAO();
		Employee e = new Employee();
		e = empDAO.getEmployee(empName);
		salary = e.getDaysOfWork() * RATE;
		e.setSalary(salary);
		
		return e;
	}
}
