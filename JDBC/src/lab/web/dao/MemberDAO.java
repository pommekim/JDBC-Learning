package lab.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import lab.web.vo.EmpDetailVO;
import lab.web.vo.EmpVO;
import lab.web.vo.MemberVO;

public class MemberDAO {
	
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
	
	
	
	//관리자 회원가입 메서드
	public void insertMember(MemberVO mem) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into member values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, mem.getUserid());
			stmt.setString(2, mem.getName());
			stmt.setString(3, mem.getPassword());
			stmt.setString(4, mem.getEmail());
			stmt.setString(5, mem.getAddress());
			stmt.executeUpdate(); //insert는 0인지 아닌지 알아보는 조건문을 걸어줄 필요없음
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO.insertMember메서드 에러발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
	}
	
	//비밀번호만 리턴해서 가입여부 확인 (교차검증이 필요없음)
	public String getPassword(String userid) {
		Connection con = null;
		String pw = null; //비밀번호를 null로 지정해둠 (null이 나왔다는 건 무조건 아이디가 틀렸다고 간주)
		try {
			con = getConnection();
			String sql = "select password from member where userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, userid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				pw = rs.getString(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO.getPassword메서드 에러발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
		return pw;
	}
	
	
	
	//관리자 정보 조회 메서드
	public MemberVO selectMember(String userid) {
		Connection con = null;
		MemberVO mem = new MemberVO();
		try {
			con = getConnection();
			String sql = "select * from member where userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, userid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				mem.setUserid(rs.getString("userid"));
				mem.setName(rs.getString("name"));
				mem.setPassword(rs.getString("password"));
				mem.setEmail(rs.getString("email"));
				mem.setAddress(rs.getString("address"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("selectMember메서드 에러발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
		return mem;
	}
	
	
	
	//관리자 정보 수정 메서드
	public void updateMember(MemberVO mem) {
		Connection con = null;
		try {
			con = getConnection();
			//내가 만든 데이터베이스 member에는 트리거(history)가 걸려있지 않으므로
			//history를 삭제하는 작업을 굳이 해줄 필요는 없음!!!
			String sql = "update member set name=?, password=?, email=?, address=? where userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, mem.getName());
			stmt.setString(2, mem.getPassword());
			stmt.setString(3, mem.getEmail());
			stmt.setString(4, mem.getAddress());
			stmt.setString(5, mem.getUserid());
			if(stmt.executeUpdate()==0) {
				con.rollback();
				throw new RuntimeException("데이터가 수정되지 않았습니다.");
			} 
		} catch(SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				
			}
			e.printStackTrace();
			throw new RuntimeException("updateMember메서드 예외발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
	}
	
	
	
	
	
	
	
	
	
	

}
