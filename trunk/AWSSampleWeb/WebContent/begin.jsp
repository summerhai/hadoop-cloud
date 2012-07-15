<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Start of GA WebApp</title>
</head>
<body>
  	<form action="UploadServlet" method="GET">
    	File to upload to S3: (2 - using S3Client and Servlet)
      	<input name="file2" type="file">
		<br>Upload file to Amazon S3 <input type="submit" value="Upload">
	</form>

	<form action="EmpServlet" method="GET">
		<table style="width: 100%;">
			<tr>
				<td class="title" colspan="2"><h1>Ship Arrangement System</h1></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>Size of Ship (weight): <input type="text" name="shipSize"
					value="${param.shipSize }" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form>


</body>
</html>