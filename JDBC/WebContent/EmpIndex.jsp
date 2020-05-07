<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
메뉴를 골라주세요.<br>
1. <a href="/JDBC/hr/EmpSearch.jsp">사원 정보 검색</a>
&nbsp;&nbsp;
2. <a href="/JDBC/Emp.do?action=list">사원 목록 조회</a> <!-- action에 매개값을 넣어줘야 함!!! -->
&nbsp;&nbsp;
3. <a href="/JDBC/Emp.do?action=insert">사원 정보 입력</a>
<br>
4. <a href="/JDBC/MemberInsert.jsp">관리자 회원 가입</a>
&nbsp;&nbsp;
5. <a href="/JDBC/MemberSearch.jsp">관리자 정보 조회</a>
<br>
6. <a href="/JDBC/Login.jsp">로그인</a>
&nbsp;&nbsp;
7. <a href="/JDBC/Login.do">로그아웃</a>
</body>
</html>