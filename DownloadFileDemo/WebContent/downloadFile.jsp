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

   // you  can get your base and parent from the database
   String base="e1";
   String parent="e2";   
   String filename=parent+"_codemiles.zip";
// you can  write http://localhost
   String filepath="http://www.codemiles.com/example/"+base+"/";

BufferedInputStream buf=null;
   OutputStream myOut=null;

try{

myOut = response.getOutputStream( );
     File myfile = new File(filepath+filename);
     
     //set response headers
     response.setContentType("text/plain");
     
     response.addHeader(
        "Content-Disposition","attachment; filename="+filename );

     response.setContentLength( (int) myfile.length( ) );
     
     FileInputStream input = new FileInputStream(myfile);
     buf = new BufferedInputStream(input);
     int readBytes = 0;

     //read from the file; write to the ServletOutputStream
     while((readBytes = buf.read( )) != -1)
       myOut.write(readBytes);

} catch (IOException ioe){
     
        //throw new ServletException(ioe.getMessage( ));
         
     } finally {
         
     //close the input/output streams
         if (myOut != null)
             myOut.close( );
          if (buf != null)
          buf.close( );
         
     }

   
   
%>
<%-- <%
  String fileName= request.getParameter("file");
  File f = new File ("D:/From desktop/" + request.getParameter("file") );
  response.setContentType ("application/pdf");
  response.addHeader ("Content-Disposition", "attachment; filename="+fileName);
  String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
  InputStream in = new FileInputStream(f);
  OutputStream outs = response.getOutputStream();
   
   
   String base="e1";
   String parent="e2";   
   String filename=parent+"_codemiles.zip";
// you can  write http://localhost
   String filepath="http://www.codemiles.com/example/"+base+"/";

  
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
 
 
%> --%>