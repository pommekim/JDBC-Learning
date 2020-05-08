<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 정보 검색</title>
</head>
<body>
<form action="/JDBC/Member.do">
<h2>관리자 정보 검색</h2>
검색하려는 관리자 아이디를 입력하세요.<br>
관리자 아이디 : <input type=text name=userid>
<input type=hidden name=action value=view>
<input type=submit value=검색></form>
</body>
</html>