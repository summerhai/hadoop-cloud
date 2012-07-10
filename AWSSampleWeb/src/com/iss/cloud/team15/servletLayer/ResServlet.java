package com.iss.cloud.team15.servletLayer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.iss.cloud.team15.logicLayer.ElasticMapReduceApp;
import com.iss.cloud.team15.util.Configuration;

/**
 * Servlet implementation class ResServlet
 */
public class ResServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Thread elasticThread = new Thread(new ElasticMapReduceApp(),"MapReduce");
			elasticThread.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String key = getKey();
		AWSCredentials myCredentials = new BasicAWSCredentials(key, getSecret()); 
		AmazonS3Client s3Client = new AmazonS3Client(myCredentials);        
		S3Object object = s3Client.getObject(new GetObjectRequest("iss.wordcount", key));

		BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
		File file = new File("localFilename");      
		Writer writer = new OutputStreamWriter(new FileOutputStream(file));

		while (true) {          
		     String line = reader.readLine();           
		     if (line == null)
		          break;            

		     writer.write(line + "\n");
		}

		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private static String getKey () {
		Configuration config = Configuration.getInstance();
		return config.getProperty("accessKey");
	}

    private static String getSecret () {
		Configuration config = Configuration.getInstance();
		return config.getProperty("secretKey");
	}
}
