<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="lab.web.vo.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 정보 검색</title>
</head>
<body>
<%MemberVO mem = (MemberVO) request.getAttribute("mem"); %>
<table>
<tr>
<td>아이디</td><td><%=mem.getUserid() %></td>
</tr>
<tr>
<td>이름</td><td><%=mem.getName() %></td>
</tr>
<tr>
<td>비밀번호</td><td><%=mem.getPassword() %></td>
</tr>
<tr>
<td>이메일</td><td><%=mem.getEmail() %></td>
</tr>
<tr>
<td>주소</td><td><%=mem.getAddress() %></td>
</tr>
</table>
<a href="/JDBC/Member.do?action=update&userid=<%=mem.getUserid()%>">수정</a>
&nbsp;&nbsp;
<a href="/JDBC/Member.do?action=delete&userid=<%=mem.getUserid()%>">삭제</a>
</body>
</html>