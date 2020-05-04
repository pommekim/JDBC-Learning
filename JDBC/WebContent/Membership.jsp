<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
<form action="/JDBC/Membership.do" method="post">
<h2>회원가입</h2>
아이디 : <input type=text name=id><br>
이름 : <input type=text name=name><br>
비밀번호 : <input type=password name=pw><br>
이메일 : <input type=text name=email><br>
주소 : <input type=text name=address><br>
<input type=submit value=가입>
</form>
</body>
</html>