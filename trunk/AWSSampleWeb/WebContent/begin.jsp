<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Start of GA WebApp</title>
</head>
<body>
	<form action="EmpServlet" method="GET">
		<table style="width: 100%;">
			<tr>
				<td colspan="2"><h1>Ship Arrangement</h1></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td class="tbl-text">Size of Ship (weight): <input type="text" name="shipSize"
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