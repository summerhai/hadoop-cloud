<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>This is a response</title>
</head>
<body>
	<form action="ResServlet" method="GET">
		<br>Step : <input type="text" name="stepName" value = "${param.stepName }"/>
		<br>Download file from Amazon S3 <input type="submit" value="download">
		<div style="color:RED">${param.error}</div>
	</form>
	
</body>
</html>