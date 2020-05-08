<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="lab.web.vo.EmpVO" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 무조건 이 한줄이 있어야 태그 라이브러리 실행 가능!!! -->
<!-- uri가 url의 더 넓은 느낌 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
</head>
<body>
<h2>사원 목록</h2>
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
<c:forEach var="emp" items="${list}">
<!-- el이랑만 호환되기 때문에 자바코드를 쓰면 안됨 -->
<!-- core태그를 쓸 때는 무조건 쌍따옴표로 묶어줘야 함!!! -->
<tr>
<td><a href="/JDBC/Emp.do?action=view&empId=${emp.employeeId}">${emp.employeeId}</a></td>
<!-- empId를 지금 누르고 있는 그 사원번호 그대로 넣어주겠다는 뜻 -->
<!-- 이름에 걸든 성에 걸든 똑같이 해주기만 하면 링크가 제대로 걸릴거임 -->
<td>${emp.firstName }</td>
<td>${emp.lastName }</td>
<td>${emp.email }</td>
<td>${emp.phoneNumber }</td>
<td>${emp.hireDate }</td>
<td>${emp.jobId }</td>
<td>${emp.salary }</td>
<td>${emp.commissionPct }</td>
<td>${emp.managerId }</td>
<td>${emp.departmentId }</td>
</tr>
</c:forEach>
</table>
</body>
</html>