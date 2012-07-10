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
//import com.iss.cloud.team15.logicLayer.ElasticMapReduceApp;
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
		/*try {
			Thread elasticThread = new Thread(new ElasticMapReduceApp(),"MapReduce");
			elasticThread.run();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	
		
		AWSCredentials myCredentials = new BasicAWSCredentials(getKey(), getSecret()); 
		AmazonS3Client s3Client = new AmazonS3Client(myCredentials);   
		String s3file = "output/Step1341934758692/part-r-00000";
		S3Object object = s3Client.getObject(new GetObjectRequest("iss.wordcount", s3file));

		BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
		File file = new File("D://localFilename");
		
		// Ram's example (start)
		OutputStream myOut = response.getOutputStream( );
		response.setContentType("text/plain");	     
	    response.addHeader("Content-Disposition","attachment; filename=localFilename");	
	    response.setContentLength( (int) file.length( ) );
	    
	    FileInputStream input = new FileInputStream(file);
	    BufferedInputStream buf = new BufferedInputStream(input);
	    
	    int readBytes = 0;
		while((readBytes = buf.read( )) != -1){
			myOut.write(readBytes); 
		}
		if (myOut != null){
			myOut.close( );
		}   
		if (buf != null){
			buf.close( );
		} 
	    // Ram's Example (end)
		
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
