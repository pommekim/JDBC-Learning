package lab.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.web.dao.MemberDAO;
import lab.web.vo.EmpDetailVO;
import lab.web.vo.EmpVO;
import lab.web.vo.MemberVO;

@WebServlet("/Member.do")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MemberDAO dao;
       
    public MemberServlet() {
        super();
        dao = new MemberDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String url = "";
		if(action.equals("view")) {
			String userid = request.getParameter("userid");
			MemberVO mem = dao.selectMember(userid);
			request.setAttribute("mem", mem);
			url = "MemberView.jsp";
			
		} else if(action.equals("insert")) {
			request.setAttribute("message", "입력");
			request.setAttribute("action", action);
			url = "MemberInsert.jsp";
		} else if(action.equals("update")) {
			String userid = request.getParameter("userid");
			MemberVO mem = dao.selectMember(userid);
			request.setAttribute("mem", mem);
			request.setAttribute("message", "수정");
			request.setAttribute("action", action);
			url = "MemberInsert.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if(action.equals("insert")) {
			String userid = request.getParameter("userid");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			
			MemberVO mem = new MemberVO();
			mem.setUserid(userid);
			mem.setName(name);
			mem.setPassword(password);
			mem.setEmail(email);
			mem.setAddress(address);
			
			dao.insertMember(mem);
			
			request.setAttribute("message", "회원 가입 성공");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			
		} else if(action.equals("update")) {
			String userid = request.getParameter("userid");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			
			MemberVO mem = new MemberVO();
			mem.setUserid(userid);
			mem.setName(name);
			mem.setPassword(password);
			mem.setEmail(email);
			mem.setAddress(address);
			
			dao.updateMember(mem);
			
			response.sendRedirect("/JDBC/Member.do?action=view&userid="+mem.getUserid());
		}
		
		
		
		
	}

}
