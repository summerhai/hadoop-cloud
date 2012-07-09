package servletLayer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logicLayer.EmpBLL;
import objectDTO.Employee;

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
		Employee e = new Employee();
		
		//String empNo = request.getParameter("empNo");
		String empName = request.getParameter("empName");
		
		//e = empBLL.getEmployee(Integer.parseInt(empNo));
		e = empBLL.getEmployee(empName);
		salary = e.getSalary();
		
		response.sendRedirect("result.jsp?empName=" + empName + "&&salary=" + salary);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
