<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="lab.web.vo.EmpDetailVO" %> <!-- EmpDetailVO 객체 임포트 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 상세 정보</title>
</head>
<body>
<table>
<!-- tr은 한줄이라는 뜻 -->
<tr>
<td>사원번호</td><td>${emp.employeeId}</td> <!-- get메서드를 통해 찾아옴 -->
<!-- property와 관련된 오류가 뜨면 get메서드 이름과 관련한거니까 그쪽을 찾아보기 -->
</tr>
<tr>
<td>이름</td><td>${emp.firstName}</td>
</tr>
<tr>
<td>성</td><td>${emp.lastName}</td>
</tr>
<tr>
<td>이메일</td><td>${emp.email}</td>
</tr>
<tr>
<td>연락처</td><td>${emp.phoneNumber}</td>
</tr>
<tr>
<td>입사일</td><td>${emp.hireDate}</td>
</tr>
<tr>
<td>직무</td><td>${emp.jobTitle}(${emp.jobId})</td>
</tr>
<tr>
<td>급여</td><td>${emp.salary}</td>
</tr>
<tr>
<td>보너스율</td><td>${emp.commissionPct}</td>
</tr>
<tr>
<td>매니저</td><td>${emp.managerName}(${emp.managerId})</td>
</tr>
<tr>
<td>부서</td><td>${emp.departmentName}(${emp.departmentId})</td>
</tr>
</table>

<a href="/JDBC/Emp.do?action=update&empId=${emp.employeeId}">정보 수정</a> <!-- 수정이나 삭제는 특정 데이터를 찍은 상태여야 함!!! -->
<!-- el에서 value값을 쓸 때는 불필요한 공백이 들어가면 안됨!!! -->
</body>
</html>