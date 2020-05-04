package lab.web.servlet;

import java.io.IOException;
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
			url = "/EmpView.jsp";
		} else if(action.equals("list")) {
			ArrayList<EmpVO> list = dao.selectEmployeeList();
			request.setAttribute("list", list);
			url = "/EmpList.jsp";
		} else if(action.equals("deptList")) {
			int deptId = Integer.parseInt(request.getParameter("deptId")); //부서번호를 int형으로 변경
			ArrayList<EmpVO> list = dao.selectEmployeeListByDeptId(deptId); //부서번호를 가지고 미리 만들어둔 메서드 실행 -> 리스트 소환
			request.setAttribute("list", list); //list라고 그대로 저장해서 /emplist 페이지에 보내주기
			url = "/EmpList.jsp";
		} else if(action.equals("manList")) {
			int manId = Integer.parseInt(request.getParameter("manId"));
			ArrayList<EmpVO> list = dao.selectEmployeeListByManId(manId);
			request.setAttribute("list", list);
			url = "/EmpList.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
