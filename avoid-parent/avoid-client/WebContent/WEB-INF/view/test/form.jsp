<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单测试</title>
</head>
<body>
<%= request.getAttribute("submission_token") %>
	<form action="/test/save" method="post">
		<input type="hidden" name="submission_token" value="<%= request.getAttribute("submission_token") %>" />
		<input type="text" name="client" >
		<input type="submit" value="提交">
	</form>
</body>
</html>