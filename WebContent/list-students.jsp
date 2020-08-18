<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Tracker</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>FooBar University</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">

			<input type="button" value="Add Student"
				onclick="window.location.href='add-student.jsp'; return false;" class="add-student-button"/>
				<table>
				<tr>	
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="student" items="${student_list}">
						
						<!-- set up link for each student -->
						
						<c:url var="tempLink" value="StudentControllerServlet">
							<c:param name="command" value="LOAD"/>
							<c:param name="studentId" value="${student.id}"/>
						</c:url>
						
						<c:url var="deleteLink" value="StudentControllerServlet">
							<c:param name="command" value="DELETE"/>
							<c:param name="studentId" value="${student.id}"/>
						</c:url>
						
						<tr>
							<td> ${student.firstName}</td>
							<td> ${student.lastName}</td>
							<td> ${student.email}</td>
							<td>
							<a href="${tempLink}">Update</a>
							|
							<a href="${deleteLink}">Delete</a>
							</td>
						</tr>
						
			    </c:forEach>
			</table>
		</div>
	</div>

</body>
</html>