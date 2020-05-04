package lab.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.web.dao.MembershipDAO;
import lab.web.vo.MembershipVO;

@WebServlet("/Membership.do")
public class MembershipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MembershipServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		MembershipVO vo = new MembershipVO();
		vo.setId(id);
		vo.setName(name);
		vo.setPw(pw);
		vo.setEmail(email);
		vo.setAddress(address);
		
		MembershipDAO dao = new MembershipDAO();
		dao.insert(vo);
		
		response.sendRedirect("/JDBC/MembershipComplete.jsp");
		
	}

}
