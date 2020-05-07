package lab.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.web.dao.MemberDAO;

@WebServlet("/Login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MemberDAO dao;
       
    public LoginServlet() {
        super();
        dao = new MemberDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate(); //세션의 invalidate메서드를 실행하면 로그아웃!
		request.setAttribute("message", "로그아웃이 완료되었습니다.");
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String pw = request.getParameter("password");
		String dbpw = dao.getPassword(userid);
		
		if(dbpw == null) {
			request.setAttribute("message", "아이디가 없습니다.");
		} else {
			if(dbpw.equals(pw)) {
				HttpSession session = request.getSession();
				session.setAttribute("userid", userid); //세션에 무언갈 저장하는 것만으로 로그인 처리가 됨
				response.sendRedirect("/JDBC/EmpIndex.jsp");
				return; //리다이렉트와 디스패쳐가 만나면 오류가 뜨기 때문에 메서드를 끝내줘야 함!!!
			} else {
				request.setAttribute("message", "비밀번호가 틀렸습니다.");
			}
		}
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
		//디스패쳐는 어차피 프로젝트 내부 데이터만 가지고 사용할 수 있기 때문에 프로젝트 주소명을 넣어주면 안됨!!!!!
	}

}
