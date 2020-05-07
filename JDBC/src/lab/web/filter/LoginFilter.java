package lab.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({"/Emp.do", "/hr/*"}) //emp서블릿과 hr폴더 밑에 있는 페이지들은 일단 걸러보겠다는 의미
public class LoginFilter implements Filter {

    public LoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest hreq = (HttpServletRequest) request; //http형태로 바꿔줘야 세션이 나올 수 있음
		HttpServletResponse hres = (HttpServletResponse) response; //마찬가지
		HttpSession session = hreq.getSession();
		if(session.getAttribute("userid")==null) {
			hres.sendRedirect("/JDBC/Login.jsp"); //메세지를 담고싶으면 디스패쳐로 보내주면 됨
			return; //체인으로 가지 못하게 막기!
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
