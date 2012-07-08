<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.io.*"%> 
<%@ page  import="java.io.FileInputStream" %>
<%@ page  import="java.io.BufferedInputStream"  %>
<%@ page  import="java.io.File"  %>
<%@ page import="java.io.IOException" %>              
<%@ page import="java.io.OutputStream" %>

<%
  String fileName= request.getParameter("file");
  File f = new File ("D:/From desktop/" + request.getParameter("file") );
  response.setContentType ("application/pdf");
  response.addHeader ("Content-Disposition", "attachment; filename="+fileName);
  String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
  InputStream in = new FileInputStream(f);
  OutputStream outs = response.getOutputStream();
  
  
  int bit = 256;
  int i = 0;
    try {
 while ((bit) >= 0) {
 bit = in.read();
 outs.write(bit);
 }
 } catch (IOException ioe) {ioe.printStackTrace(System.out);
 }
 outs.flush();
 outs.close();
 in.close(); 
%>