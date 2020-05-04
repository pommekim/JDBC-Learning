<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/JDBC/Emp.do">
<h2>사원 정보 검색</h2>
검색하려는 사원의 사원번호를 입력하세요.<br>
사원번호 : <input type=text name=empId>
<input type=hidden name=action value=view>
<input type=submit value=검색>
</form>
</body>
</html>