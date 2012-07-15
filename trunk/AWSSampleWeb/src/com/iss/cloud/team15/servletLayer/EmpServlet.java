package com.iss.cloud.team15.servletLayer;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.iss.cloud.team15.logicLayer.ElasticMapReduceApp;
import com.iss.cloud.team15.logicLayer.ElasticMapReduceApp;
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
		String shipWeight = request.getParameter("shipSize");
		String stepName = null;
		try {
			//Thread elasticThread = new Thread(new ElasticMapReduceApp(),"MapReduce");
			//elasticThread.run();
			//ElasticMapReduceApp mapreduce = new ElasticMapReduceApp();
			//mapreduce.setWeight(shipWeight);
			//mapreduce.run();
			//stepName = mapreduce.getStep();
			stepName = "123123";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("result.jsp?stepName=" + stepName);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
