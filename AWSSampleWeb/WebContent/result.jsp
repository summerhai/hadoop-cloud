<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>This is a response</title>
</head>
<body>
	<h3>This is a response</h3>
	<form action="ResServlet" method="GET">
		<br>Employee : ${param.empName}
		<br>Salary : ${param.salary}
		<br>Download file from Amazon S3 <input type="submit" value="download">
	</form>
</body>
</html>