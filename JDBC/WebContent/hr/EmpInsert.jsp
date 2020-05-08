<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "lab.web.vo.*" %>
<%@ page import = "java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 정보 ${message}</title> <!-- 애초에 컨트롤러에서 구분되어 있기 때문에 그것을 활용 -->
</head>
<body>
<h2>사원 정보 ${message}</h2>
<form action="/JDBC/Emp.do" method=post>
<input type=hidden name=action value="${action}"> <!-- action이 설정이 안되어있으면 post에서 구분을 못함!!! -->
<!-- 수정 버전만 살려두고 싹 다 날림 -->
<table>
<tr>
<td>사원번호</td><td><input type=text name=empId value="${emp.employeeId}" ${empty emp ? "" : "readonly"}></td>
<!-- readonly : 수정 안되고 읽기만! -->
<!-- 3항 다항식을 이용해서 emp가 비어있지 않을때만 readonly를 써줌 -->
</tr>
<tr>
<td>이름, 성</td>
<td><input type=text name=firstName value="${emp.firstName}"><input type=text name=lastName value="${emp.lastName}"></td>
</tr>
<tr>
<td>이메일</td><td><input type=text name=email value="${emp.email}"></td>
</tr>
<tr>
<td>연락처</td><td><input type=text name=phoneNumber value="${emp.phoneNumber}"></td>
</tr>
<tr>
<td>입사일</td><td><input type=date name=hireDate value="${emp.hireDate}"></td> <!-- 그냥 써도 됨 -->
</tr>
<tr>
<td>직무</td><td><select name=jobId> <!-- 직무는 다른 방식으로!!! -->
<c:forEach var="job" items="${jobList}">
<option value="${job.jobId}" ${emp.jobId eq job.jobId ? "selected" : ""}>
${job.jobTitle}
<!-- 직무, 매니저, 부서 모두 3항 다항식으로 처리 -->
<%--선택했을때 넘어갈 값을 value에 넣는다 --%>
</c:forEach>
</select></td></tr>
<tr>
<td>급여</td><td><input type=text name=salary value="${emp.salary}"></td>
</tr>
<tr>
<td>보너스율</td><td><input type=number min=0 max=0.95 name=commissionPct step=0.05 value="${emp.commissionPct}"></td>
</tr>
<tr>
<td>매니저</td><td><select name=managerId>
<c:forEach var="man" items="${manList}">
<option value="${man.employeeId}" ${emp.managerId eq man.employeeId ? "selected" : ""}>
${man.firstName}
</c:forEach>
</select></td></tr>
<tr>
<td>부서</td><td><select name=departmentId>
<c:forEach var="dept" items="${deptList}">
<option value="${dept.departmentId}" ${emp.departmentId eq dept.departmentId ? "selected" : ""}>
${dept.departmentName}
</c:forEach>
</select></td></tr>
<tr>
<td><input type=submit value="${message}">
<input type=reset value=취소>
</td></tr>
</table>
</form>
</body>
</html>