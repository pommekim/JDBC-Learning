<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import = "lab.web.vo.*" %>
<%@page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 정보 <%=request.getAttribute("message") %></title> <!-- 애초에 컨트롤러에서 구분되어 있기 때문에 그것을 활용 -->
</head>
<body>
<%EmpVO emp = (EmpVO) request.getAttribute("emp"); %>
<h2>사원 정보 <%=request.getAttribute("message") %></h2>
<form action="/JDBC/Emp.do" method=post>
<%if(emp==null) { %>
<input type=hidden name=action value="<%=request.getAttribute("action") %>"> <!-- action이 설정이 안되어있으면 post에서 구분을 못함!!! -->
<table>
<tr>
<td>사원번호</td><td><input type=text name=empId></td>
</tr>
<tr>
<td>이름, 성</td>
<td><input type=text name=firstName><input type=text name=lastName></td>
</tr>
<tr>
<td>이메일</td><td><input type=text name=email></td>
</tr>
<tr>
<td>연락처</td><td><input type=text name=phoneNumber></td>
</tr>
<tr>
<td>입사일</td><td><input type=date name=hireDate></td>
</tr>
<%ArrayList<JobVO> jobList = (ArrayList<JobVO>) request.getAttribute("jobList"); %>
<tr>
<td>직무</td><td><select name=jobId>
<%for(JobVO job : jobList){ %>
<option value="<%=job.getJobId() %>"><%=job.getJobTitle() %> <%--선택했을때 넘어갈 값을 value에 넣는다 --%>
<% } %>
</select></td></tr>
<tr>
<td>급여</td><td><input type=text name=salary></td>
</tr>
<tr>
<td>보너스율</td><td><input type=number min=0 max=0.95 name=commissionPct step=0.05></td>
</tr>
<%ArrayList<EmpVO> manList = (ArrayList<EmpVO>) request.getAttribute("manList"); %>
<tr>
<td>매니저</td><td><select name=managerId>
<%for(EmpVO man : manList) { %>
<option value="<%=man.getEmployeeId() %>">
<%=man.getFirstName() %>
<% } %>
</select></td></tr>
<%ArrayList<DeptVO> deptList = (ArrayList<DeptVO>) request.getAttribute("deptList"); %>
<tr>
<td>부서</td><td><select name=departmentId>
<%for(DeptVO dept: deptList) { %>
<option value="<%=dept.getDepartmentId() %>">
<%=dept.getDepartmentName() %>
<% } %>
</select></td></tr>
<tr>
<td><input type=submit value="<%=request.getAttribute("action") %>">
<input type=reset value=취소>
</td></tr>
</table>
<% } else { %> <!-- table을 통째로 복사해오기, 여기에 수정 페이지 작성 -->
<table>
<tr>
<td>사원번호</td><td><input type=text name=empId value="<%=emp.getEmployeeId() %>"></td>
</tr>
<tr>
<td>이름, 성</td>
<td><input type=text name=firstName value="<%=emp.getFirstName() %>"><input type=text name=lastName value="<%=emp.getLastName() %>"></td>
</tr>
<tr>
<td>이메일</td><td><input type=text name=email value="<%=emp.getEmail() %>"></td>
</tr>
<tr>
<td>연락처</td><td><input type=text name=phoneNumber value="<%=emp.getPhoneNumber() %>"></td>
</tr>
<tr>
<td>입사일</td><td><input type=date name=hireDate value="<%=emp.getHireDate() %>"></td> <!-- 그냥 써도 됨 -->
</tr>
<%ArrayList<JobVO> jobList = (ArrayList<JobVO>) request.getAttribute("jobList"); %>
<tr>
<td>직무</td><td><select name=jobId> <!-- 직무는 다른 방식으로!!! -->
<%for(JobVO job : jobList){ %>
<option value="<%=job.getJobId() %>" <%=emp.getJobId().equals(job.getJobId()) ? "selected" : "" %>>
<%=job.getJobTitle() %>
<!-- 직무, 매니저, 부서 모두 3항 다항식으로 처리 -->
<%--선택했을때 넘어갈 값을 value에 넣는다 --%>
<% } %>
</select></td></tr>
<tr>
<td>급여</td><td><input type=text name=salary value="<%=emp.getSalary() %>"></td>
</tr>
<tr>
<td>보너스율</td><td><input type=number min=0 max=0.95 name=commissionPct step=0.05 value="<%=emp.getCommissionPct() %>"></td>
</tr>
<%ArrayList<EmpVO> manList = (ArrayList<EmpVO>) request.getAttribute("manList"); %>
<tr>
<td>매니저</td><td><select name=managerId>
<%for(EmpVO man : manList) { %>
<option value="<%=man.getEmployeeId() %>" <%=emp.getManagerId()==man.getEmployeeId() ? "selected" : "" %>>
<%=man.getFirstName() %>
<% } %>
</select></td></tr>
<%ArrayList<DeptVO> deptList = (ArrayList<DeptVO>) request.getAttribute("deptList"); %>
<tr>
<td>부서</td><td><select name=departmentId>
<%for(DeptVO dept: deptList) { %>
<option value="<%=dept.getDepartmentId() %>" <%=emp.getDepartmentId()==dept.getDepartmentId() ? "selected" : ""%>>
<%=dept.getDepartmentName() %>
<% } %>
</select></td></tr>
<tr>
<td><input type=submit value="<%=request.getAttribute("action") %>">
<input type=reset value=취소>
</td></tr>
</table>
<% } %> <!-- 테이블이 끝나는 지점에 if문 닫기 -->
</form>
</body>
</html>