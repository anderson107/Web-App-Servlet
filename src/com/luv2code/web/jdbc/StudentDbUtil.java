package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;
	
	public StudentDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Student>getStudents()throws Exception{
		
		List<Student> students = new ArrayList<>();
		
		Connection conn = null;
		Statement stm = null;
		ResultSet result = null;
			
		try {
			conn = dataSource.getConnection();
			
			String sql = "select*from student order by last_name";
			
			stm = conn.createStatement();
			
			result = stm.executeQuery(sql);
			
			while(result.next()) {
				
			int id = result.getInt("id");
			String firstName = result.getString("first_name");
			String lastName = result.getString("last_name");
			String email = result.getString("email");
			
			Student student = new Student(id, firstName, lastName, email);
			
			students.add(student);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
			stm.close();
			result.close();
		}
		return students;
	}

	public void addStudent(Student student) throws Exception {
		
		Connection conn = null;
		PreparedStatement smt = null;
		
		try {
			
			conn = dataSource.getConnection();
			
			String sql = "insert into student "
					+ "(first_name, last_name, email)"
					+ "values (?, ?, ?)";
			
			smt = conn.prepareStatement(sql);
			
			smt.setString(1, student.getFirstName());
			smt.setString(2, student.getLastName());
			smt.setString(3, student.getEmail());
			
			smt.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
			smt.close();
		}
		
	}

	public Student getStudent(String studentId) throws Exception {
		
		Student student = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet result = null;
		int id;
		
		try {
			
			int studId = Integer.parseInt(studentId);
			
			conn = dataSource.getConnection();
			
			String sql = "select * from student where id=?";
			
			pst = conn.prepareStatement(sql);
			
			pst.setInt(1, studId);
			
			result = pst.executeQuery();
			
			if(result.next()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String email = result.getString("email");
				
				student = new Student(studId, firstName, lastName, email);
			}else {
				throw new Exception("Could not find student id");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
			pst.close();
			result.close();
		}
		return student;
	}

	public void updateStudent(Student student) throws Exception {
		
		Connection conn = null;
		
		PreparedStatement pst = null;
		
		try {
			
			conn = dataSource.getConnection();
			
			String sql = "update student "
					+ "set first_name=?, last_name=?, email=? "
					+ "where id=?";
			
			pst = conn.prepareStatement(sql);
			pst.setString(1, student.getFirstName());
			pst.setString(2, student.getLastName());
			pst.setString(3, student.getEmail());
			pst.setInt(4, student.getId());
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
			pst.close();
		}
		
	}

	public void deleteStudent(String id) throws Exception{
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			
			int studentId = Integer.parseInt(id);
			
			conn = dataSource.getConnection();
			
			String sql = "delete from student where id=?";
			
			pst = conn.prepareStatement(sql);
			
			pst.setInt(1, studentId);
			
			pst.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
			pst.close();
			
		}
	}
}
