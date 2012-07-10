package com.iss.cloud.team15.servletLayer;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.iss.cloud.team15.logicLayer.ElasticMapReduceApp;
import com.iss.cloud.team15.logicLayer.EmpBLL;
import com.iss.cloud.team15.objectDTO.Employee;


/**
 * Servlet implementation class EmpServlet
 */
public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		float salary;
		EmpBLL empBLL = new EmpBLL();
		Employee emp = new Employee();
		
		//String empNo = request.getParameter("empNo");
		String empName = request.getParameter("empName");
		
		//e = empBLL.getEmployee(Integer.parseInt(empNo));
		emp = empBLL.getEmployee(empName);
		salary = emp.getSalary();
		/*try {
			Thread elasticThread = new Thread(new ElasticMapReduceApp(),"MapReduce");
			elasticThread.run();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		response.sendRedirect("result.jsp?empName=" + empName + "&&salary=" + salary);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
