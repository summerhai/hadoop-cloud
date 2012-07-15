<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>This is a response</title>
<link rel="stylesheet" href="styles/styles.css" type="text/css"
	media="screen">
</head>
<body>
	<form action="ResServlet" method="GET">
		<table style="width: 100%;">
			<tr>
				<td colspan="2"><h1>Result Download</h1></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td class="tbl-text">Step : <input type="text" name="stepName"
					value="${param.stepName }" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td class="tbl-text">Download file from Amazon S3 <input
					type="submit" value="Download"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td style="color: RED">${param.error}</td>
			</tr>
		</table>
	</form>

	<%-- 
	<%@ page import="com.iss.cloud.team15.logicLayer.AuthUploadFiles" %>
	<%	AuthUploadFiles upload = new AuthUploadFiles(); 
		upload.authUpload();
	%>
	<form action="https://mybucketphoto.s3.amazonaws.com/" method="post" enctype="multipart/form-data">
      <input type="hidden" name="key" value="uploads/${filename}">
      <input type="hidden" name="AWSAccessKeyId" value="<%=upload.getAccessKey()%>"> 
      <input type="hidden" name="acl" value="private"> 
      <input type="hidden" name="success_action_redirect" value="http://localhost:8080/AWSSampleWeb/result.jsp">
      <input type="hidden" name="policy" value="<%=upload.getPolicy()%>">
      <input type="hidden" name="signature" value="<%=upload.getSignature()%>">
      <input type="hidden" name="Content-Type" value="plain/text">
      <input type="hidden" name="redirect" value="http://www.telescource.com" />
      <!-- Include any additional input fields here -->

      File to upload to S3: (1 - using POST HTML Form)
      <input name="file1" type="file"> 
      <br> 
      Upload file to S3 <input type="submit" value="Upload"> 
    </form> --%>


</body>
</html>