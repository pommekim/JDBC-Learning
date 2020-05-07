package lab.web.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.web.dao.EmpDAO;
import lab.web.vo.EmpDetailVO;
import lab.web.vo.EmpVO;

@WebServlet("/Emp.do")
public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	EmpDAO dao;
       
    public EmpServlet() {
        super();
        dao = new EmpDAO(); //dao객체가 만들어지는 순간 커넥션풀이 실행됨
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		EmpVO emp = dao.selectEmployee(100);
//		System.out.println(emp);
		
		request.setCharacterEncoding("UTF-8"); //한글이 언제 들어올지 모르니 인코딩 설정
		String action = request.getParameter("action");
		String url = "";
		if(action.equals("view")) {
			int empId = Integer.parseInt(request.getParameter("empId")); //String으로 들어온 파라미터를 int로 변경
			EmpDetailVO emp = dao.selectEmployee(empId);
			request.setAttribute("emp", emp); //요청객체에 데이터를 넘겨줌
			url = "/hr/EmpView.jsp";
		} else if(action.equals("list")) {
			ArrayList<EmpVO> list = dao.selectEmployeeList();
			request.setAttribute("list", list);
			url = "/hr/EmpList.jsp";
		} else if(action.equals("deptList")) {
			int deptId = Integer.parseInt(request.getParameter("deptId")); //부서번호를 int형으로 변경
			ArrayList<EmpVO> list = dao.selectEmployeeListByDeptId(deptId); //부서번호를 가지고 미리 만들어둔 메서드 실행 -> 리스트 소환
			request.setAttribute("list", list); //list라고 그대로 저장해서 /emplist 페이지에 보내주기
			url = "/hr/EmpList.jsp";
		} else if(action.equals("manList")) {
			int manId = Integer.parseInt(request.getParameter("manId"));
			ArrayList<EmpVO> list = dao.selectEmployeeListByManId(manId);
			request.setAttribute("list", list);
			url = "/hr/EmpList.jsp";
			
		} else if(action.equals("insert")) {
			request.setAttribute("jobList", dao.selectJobs()); //arraylist 결과값을 보내주겠다!
			request.setAttribute("manList", dao.selectManagers());
			request.setAttribute("deptList", dao.selectDepartments());
			request.setAttribute("message", "입력");
			request.setAttribute("action", action); //입력일 때 실행이 되게끔!
			url = "/hr/EmpInsert.jsp";
		} else if(action.equals("update")) {
			int empId = Integer.parseInt(request.getParameter("empId")); //들어올 사원번호 받아주기
			EmpVO emp = dao.selectEmployee(empId);
			request.setAttribute("emp", emp);
			request.setAttribute("jobList", dao.selectJobs()); //위에 list에 있는 것 전부 함께 보내주기
			request.setAttribute("manList", dao.selectManagers());
			request.setAttribute("deptList", dao.selectDepartments());
			request.setAttribute("message", "수정");
			request.setAttribute("action", action); //수정일 때 실행이 되게끔!
			url = "/hr/EmpInsert.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action"); //action을 통해 insert와 update를 나누어 줌
		
		if(action.equals("insert")) {
			int empId = Integer.parseInt(request.getParameter("empId"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			java.sql.Date hireDate = null; //sql의 date이기 때문에 한번 더 재정의해줘야 함
			try {
				String sdate = request.getParameter("hireDate");
				SimpleDateFormat tool = new SimpleDateFormat("yyyy-MM-dd"); //1. 형식 바꾸기
				java.util.Date date = tool.parse(sdate); //2. simple의 parse메서드를 이용해서 자바 날짜로 바꾸기 (여기서 exception이 걸릴 수 있음!!!)
				hireDate = new java.sql.Date(date.getTime()); //3. sql date로 다시 한번 바꾸기 (자바날짜를 숫자로 바꿔서 sql로 넣어주는 것)
			} catch(ParseException e) {
				e.printStackTrace();
			}
			String jobId = request.getParameter("jobId");
			double salary = Double.parseDouble(request.getParameter("salary")); //클래스형 Double을 기본형 double로 바꿔주기
			double commissionPct = Double.parseDouble(request.getParameter("commissionPct"));
			int managerId = Integer.parseInt(request.getParameter("managerId"));
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			
			EmpVO emp = new EmpVO();
			emp.setEmployeeId(empId);
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			emp.setEmail(email);
			emp.setPhoneNumber(phoneNumber);
			emp.setHireDate(hireDate);
			emp.setJobId(jobId);
			emp.setSalary(salary);
			emp.setCommissionPct(commissionPct);
			emp.setManagerId(managerId);
			emp.setDepartmentId(departmentId);
			dao.insertEmployee(emp); //입력 메서드 실행!
			response.sendRedirect("/JDBC/Emp.do?action=list"); //주소값 주의
			
		} else if(action.equals("update")) {
			int empId = Integer.parseInt(request.getParameter("empId"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			java.sql.Date hireDate = null; //sql의 date이기 때문에 한번 더 재정의해줘야 함
			try {
				String sdate = request.getParameter("hireDate");
				SimpleDateFormat tool = new SimpleDateFormat("yyyy-MM-dd"); //1. 형식 바꾸기
				java.util.Date date = tool.parse(sdate); //2. simple의 parse메서드를 이용해서 자바 날짜로 바꾸기 (여기서 exception이 걸릴 수 있음!!!)
				hireDate = new java.sql.Date(date.getTime()); //3. sql date로 다시 한번 바꾸기 (자바날짜를 숫자로 바꿔서 sql로 넣어주는 것)
			} catch(ParseException e) {
				e.printStackTrace();
			}
			String jobId = request.getParameter("jobId");
			double salary = Double.parseDouble(request.getParameter("salary")); //클래스형 Double을 기본형 double로 바꿔주기
			double commissionPct = Double.parseDouble(request.getParameter("commissionPct"));
			int managerId = Integer.parseInt(request.getParameter("managerId"));
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			
			EmpVO emp = new EmpVO();
			emp.setEmployeeId(empId);
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			emp.setEmail(email);
			emp.setPhoneNumber(phoneNumber);
			emp.setHireDate(hireDate);
			emp.setJobId(jobId);
			emp.setSalary(salary);
			emp.setCommissionPct(commissionPct);
			emp.setManagerId(managerId);
			emp.setDepartmentId(departmentId);
			dao.updateEmployee(emp); //수정 메서드 실행!
			response.sendRedirect("/JDBC/Emp.do?action=view&empId="+emp.getEmployeeId()); //지금 보고 있는 사원의 사원번호를 받아줘야 함
			
		}
		
		
		
		
		
		
		
		
	}

}
