<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="lab.web.vo.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 정보 <%=request.getAttribute("message")%></title>
</head>
<body>
<%MemberVO mem = (MemberVO) request.getAttribute("mem"); %>
<h2>관리자 정보 <%=request.getAttribute("message")%></h2>
<form action="/JDBC/Member.do" method="post">
<input type=hidden name=action value="<%=request.getAttribute("action")%>">
<%if(mem==null) { %>
<table>
<tr>
<td>아이디</td><td><input type=text name=userid></td>
</tr>
<tr>
<td>이름</td><td><input type=text name=name></td>
</tr>
<tr>
<td>비밀번호</td><td><input type=password name=password></td>
</tr>
<tr>
<td>이메일</td><td><input type=text name=email></td>
</tr>
<tr>
<td>주소</td><td><input type=text name=address></td>
</tr>
<tr>
<td><input type=submit value="<%=request.getAttribute("action")%>">
<input type=reset value=취소></td>
</tr>
</table>
<% } else { %>
<table>
<tr>
<td>아이디</td><td><input type=text name=userid value="<%=mem.getUserid() %>"></td>
</tr>
<tr>
<td>이름</td><td><input type=text name=name value="<%=mem.getName() %>"></td>
</tr>
<tr>
<td>비밀번호</td><td><input type=password name=password value="<%=mem.getPassword() %>"></td>
</tr>
<tr>
<td>이메일</td><td><input type=text name=email value="<%=mem.getEmail() %>"></td>
</tr>
<tr>
<td>주소</td><td><input type=text name=address value="<%=mem.getAddress() %>"></td>
</tr>
<tr>
<td><input type=submit value="<%=request.getAttribute("action")%>">
<input type=reset value=취소></td>
</tr>
</table>
<% } %>
</form>
</body>
</html>