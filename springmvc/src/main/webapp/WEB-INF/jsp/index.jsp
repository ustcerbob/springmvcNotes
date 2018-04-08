<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
helloworld:<a href="${pageContext.request.contextPath }/helloWorld">helloWorld</a>
<hr>
<!-- 
<form action="${pageContext.request.contextPath }/testModelAttribute" method="post">
	<input type="hidden" name="_method" value="put"/>
	id:<input type="text" name="id" value="11"/><br>
	username:<input type="text" name="username" value="lsw"/><br>
	age:<input type="text" name="age" value="26"/><br>
	<input type="submit" value="testModelAttribute"/>
</form>
 -->
</body>
</html>