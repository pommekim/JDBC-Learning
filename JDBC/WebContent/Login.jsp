<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
${message} <!-- EL표현식 : null값이면 자동으로 아무것도 출력하지 않음!!! -->
<form action="/JDBC/Login.do" method=post>
<%session = request.getSession(); %>
<%if(session.getAttribute("userid")==null) { %>
아이디 : <input type=text name=userid><br>
비밀번호 : <input type=password name=password><br>
<input type=submit value=로그인>
<% } else { %>
이미 로그인이 되어있는 상태입니다.<br>
<a href="/JDBC/EmpIndex.jsp">되돌아가기</a>
<% } %>
</form>
</body>
</html>