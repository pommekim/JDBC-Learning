package lab.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import lab.web.vo.MembershipVO;

public class MembershipDAO {
	
	//★개인실습 - 회원가입 폼 만들어보기★
	
	static {
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		}
	}
	
	private Connection getConnection() {
		DataSource ds = null;
		Connection con = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			con = ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	private void closeConnection(Connection con) {
		if(con != null) {
			try {con.close();} catch(SQLException e) {}
		}
	}
	
	public void insert(MembershipVO membership) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into membership values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, membership.getId());
			stmt.setString(2, membership.getName());
			stmt.setString(3, membership.getPw());
			stmt.setString(4, membership.getEmail());
			stmt.setString(5, membership.getAddress());
			stmt.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("insert메서드 에러발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
