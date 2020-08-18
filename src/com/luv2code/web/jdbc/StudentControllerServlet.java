package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDB;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			
			studentDB = new StudentDbUtil(dataSource);
			
		}catch(Exception e) {
			throw new ServletException();
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// read the command parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing
			if(theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch(theCommand) {
			case "LIST": listStudents(request, response);
			break;
			case "ADD":
				addStudent(request, response);
				break;
		
			case "LOAD":
				loadStudent(request, response);
				break;
			
			case "UPDATE":
				updateStudent(request, response);
				break;
				
			case "DELETE":
				deleteStudent(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String id = request.getParameter("studentId");
		
		studentDB.deleteStudent(id);
		
		listStudents(request, response);
	}


	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student student = new Student(id, firstName, lastName, email);
		
		studentDB.updateStudent(student);
		
		listStudents(request, response);
		
	}


	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String studentId = request.getParameter("studentId");
		
		Student theStudent = studentDB.getStudent(studentId);
		
		request.setAttribute("THE_STUDENT", theStudent);
		
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/update-student-form.jsp");
		
		dispatcher.forward(request, response);
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student student = new Student(firstName, lastName, email);
		
		studentDB.addStudent(student);
		
		listStudents(request, response);
		
	}


	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		List<Student>studentList = studentDB.getStudents();
		
		request.setAttribute("student_list", studentList);
		
		RequestDispatcher dispacher = request.getRequestDispatcher("/list-students.jsp");
		
		dispacher.forward(request, response);
	}

}
