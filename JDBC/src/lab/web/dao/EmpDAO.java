package lab.web.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import lab.web.vo.EmpDetailVO;
import lab.web.vo.EmpVO;

public class EmpDAO {
	
	static { //초기화자에 공유할 수 있는 static 붙여주기 (드라이버 로드)
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		}
	}
	
	private Connection getConnection() { //커넥션풀에서 커넥션 찾아오는 메서드 만들기
		DataSource ds = null; //데이터 소스 객체 생성
		Connection con = null; //사전에 생성해둔 데이터풀 객체 생성
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
	
	//---------------------------------------------------------------------------------------------------
	
	public EmpDetailVO selectEmployee(int empId) {
		Connection con = null;
		EmpDetailVO emp = new EmpDetailVO(); //데이터를 담을 수 있는 VO객체를 생성
		try {
			con = getConnection(); //커넥션 만들기
			String sql = "select emp.employee_id, first_name, last_name, email, phone_number, hire_date, "
					+ "emp.job_id, job_title, salary, commission_pct, emp.manager_id, manager_name, "
					+ "emp.department_id, department_name from employees emp " //쿼리를 나눠쓸 때 띄워쓰기 조심!!!
					+ "left join jobs job " //join에 모두 left를 걸어주기 (기준인 emp시트에 맞춰주기 위해서)
					+ "on emp.job_id=job.job_id "
					+ "left join (select employee_id, first_name||' '||last_name as manager_name from employees "
					+ "where employee_id in (select distinct manager_id from employees)) man " //매니저 아이디에서 중복 제거
					+ "on emp.manager_id=man.employee_id "
					+ "left join departments dept "
					+ "on emp.department_id=dept.department_id "
					//지금 생성한 쿼리열(jobTitle, managerName, departmentName)을 담아줄 수 있는 새로운 VO를 하나 생성!
					+ "where emp.employee_id=?"; //쿼리 작성, 자리에 들어갈 것이 바뀔때는 ?로 표현
			PreparedStatement stmt = con.prepareStatement(sql); //쿼리를 셋팅하고 실행하는 클래스
			stmt.setInt(1, empId); //db기준이기 때문에 1을 넣어주어야 함, ?가 채워졌기 때문에 쿼리 실행 가능!!!
			
			ResultSet rs = stmt.executeQuery(); //ResultSet는 테이블 형태를 담아낼 수 있는 객체 클래스
			//executeUpdate는 int를 반환 (insert, update, delete 쿼리문)
			//executeQuery는 ResultSet을 반환 (그 외)
			if(rs.next()) { //db의 이터레이터 -> next (행을 하나씩 옮기면서 행에 있는 데이터 값들을 하나씩 빼옴)
				emp.setEmployeeId(rs.getInt("employee_id")); //어떤 타입으로 받아올건지 지정 후 만들어둔 클래스에 바로 저장
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct")); //이름뿐만 아니라 몇번째인지도 쓸 수 있음
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				
				emp.setJobTitle(rs.getString("job_title")); //새롭게 만들어준 열들 추가
				emp.setManagerName(rs.getString("manager_name"));
				emp.setDepartmentName(rs.getString("department_name"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("selectEmployee메서드 에러발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
		return emp;
	}
	
	//---------------------------------------------------------------------------------------------------
	
	//사원들의 목록을 조회하는 메서드
	public ArrayList<EmpVO> selectEmployeeList() { //목록을 띄울거면 무조건 페이지 번호가 들어가야 함 (지금은 스킵)
		Connection con = null;
		ArrayList<EmpVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from employees";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) { //모든 데이터를 다 긁어와야하기 때문에 while문 사용
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble(9));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				list.add(emp); //객체 생성 후 모든 데이터를 담은 후 리스트에 저장!!!!!
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("selectEmployeeList메서드 에러발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
		return list; //리스트를 반환
	}
	
	//---------------------------------------------------------------------------------------------------
	
	//부서번호 조회를 통해 해당 부서번호인 사원 목록 전부 띄우기
	public ArrayList<EmpVO> selectEmployeeListByDeptId(int deptId) {
		Connection con = null; //커넥션 미리 선언 (try catch문이 끝나고 난 후에 닫아주기 위해서)
		ArrayList<EmpVO> list = new ArrayList<>(); //리턴할 값이 있기 때문에 리스트 객체 생성
		try {
			con = getConnection(); //커넥션 연결
			String sql = "select * from employees where department_id=?"; //부서번호를 ?로 처리
			PreparedStatement stmt = con.prepareStatement(sql); //sql을 실행시켜줄 prepared 객체 생성
			stmt.setInt(1, deptId); //?를 채워주기 위해 set메서드 활용
			ResultSet rs = stmt.executeQuery(); //쿼리문 실행 후 resultset을 통해 결과값을 받아옴 (테이블 형식으로)
			
			while(rs.next()) {
				EmpVO emp = new EmpVO(); //정보를 담아줄 객체 생성
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				list.add(emp);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("selectEmployeeListByDeptId메서드 에러발생-콘솔확인"); //예외처리
		} finally {
			closeConnection(con); //커넥션 닫기
		}
		return list;
	}
	
	//---------------------------------------------------------------------------------------------------
	
	//(문제)매니저 번호를 입력해서 그 매니저를 가진 사람만 뜨게 하시오
	//결국은 쿼리문만 바뀌게 됨
	public ArrayList<EmpVO> selectEmployeeListByManId(int manId) {
		Connection con = null;
		ArrayList<EmpVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from employees where manager_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, manId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				list.add(emp);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("selectEmployeeListByManId메서드 에러발생-콘솔확인");
		} finally {
			closeConnection(con);
		}
		return list;
	}
	
	//---------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
