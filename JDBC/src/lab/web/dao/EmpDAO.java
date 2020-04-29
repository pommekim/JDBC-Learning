package lab.web.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import lab.web.vo.EmpVO;

public class EmpDAO {
	
	static { //초기화자에 공유할 수 있는 static 붙여주기
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		}
	}
	
	private Connection getConnection() { //커넥션풀에서 커넥션 찾아오는 메서드 만들기
		DataSource ds = null;
		Connection con = null;
		try {
			Context ctx = new InitialContext(); //context는 프로젝트를 객체화 시켜놓은 것
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle"); //context를 통해 저 주소값에 데이터를 찾아와라?
			con = ds.getConnection(); //connection 가져오기
		} catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	private void closeConnection(Connection con) { //커넥션 반납하는 메서드 만들기
		if(con != null) {
			try {con.close();} catch(SQLException e) {}
		}
	}
	
	public EmpVO selectEmployee(int empId) {
		Connection con = null;
		EmpVO emp = new EmpVO();
		try {
			con = getConnection(); //커넥션 만들기
			String sql = "select * from employees where employee_id=?"; //쿼리 작성, 자리에 들어갈 것이 바뀔때는 ?로 표현
			PreparedStatement stmt = con.prepareStatement(sql); //쿼리를 셋팅하고 실행하는 클래스
			stmt.setInt(1, empId); //db기준이기 때문에 1을 넣어주어야 함, ?가 채워졌기 때문에 쿼리 실행 가능!!!
			
			ResultSet rs = stmt.executeQuery(); //ResultSet는 테이블 형태를 본따서 만든 클래스
			if(rs.next()) { //db의 이터레이터 -> next (행을 하나씩 옮기면서 행에 있는 데이터 값들을 하나씩 빼옴)
				emp.setEmployeeId(rs.getInt("employee_id")); //어떤 타입으로 받아올건지 지정 후 만들어둔 클래스에 바로 저장
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble(9));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("departement_id"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("selectEmployee메서드 에러발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
		return emp;
	}
	
	
	
	
	
	
	
	
	
	

}
