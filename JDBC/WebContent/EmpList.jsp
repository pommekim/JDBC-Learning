<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="lab.web.vo.EmpVO" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
</head>
<body>
<h2>사원 목록</h2>
<%ArrayList<EmpVO> list = (ArrayList<EmpVO>) request.getAttribute("list"); %>
<table>
<tr>
<td>사원번호</td>
<td>이름</td>
<td>성</td>
<td>이메일</td>
<td>연락처</td>
<td>입사일</td>
<td>직무</td>
<td>급여</td>
<td>보너스율</td>
<td>매니저</td>
<td>부서</td>
</tr>
<%for(EmpVO emp : list) { %>
<tr>
<td><a href="/JDBC/Emp.do?action=view&empId=<%=emp.getEmployeeId()%>"><%=emp.getEmployeeId()%></a></td>
<!-- empId를 지금 누르고 있는 그 사원번호 그대로 넣어주겠다는 뜻 -->
<!-- 이름에 걸든 성에 걸든 똑같이 해주기만 하면 링크가 제대로 걸릴거임 -->
<td><%=emp.getFirstName()%></td>
<td><%=emp.getLastName()%></td>
<td><%=emp.getEmail() %></td>
<td><%=emp.getPhoneNumber() %></td>
<td><%=emp.getHireDate() %></td>
<td><%=emp.getJobId() %></td>
<td><%=emp.getSalary() %></td>
<td><%=emp.getCommissionPct() %></td>
<td><%=emp.getManagerId() %></td>
<td><%=emp.getDepartmentId() %></td>
</tr>
<% } %>
</table>
</body>
</html>