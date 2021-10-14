package com.vietjack.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.vietjack.core.Student;

public class StudentDAO {
	private Connection conn;

	public Connection getConnection() throws SQLException {
		if (conn == null) {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo", "root", "123456");
			return conn;
		}
		return conn;
	}
	
	public void closeConnection(){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean addNewStudent(Student student) throws SQLException{
		String query = "insert into STUDENT(id,name,email) values (" + student.getId() + ",'" + student.getName()
				+ "','" + student.getEmail() + "')";		
		Statement stmt = getConnection().createStatement();
		int n = stmt.executeUpdate(query);
		// return (n!=0);
		if (n != 0)
			return true;
		return false;
	}
	public boolean modifyStudent(Student student) throws SQLException{
		String query = "update STUDENT set name='" + student.getName() + "' , email='" + student.getEmail() + "' where id="+student.getId();
		Statement stmt = getConnection().createStatement();
		int n = stmt.executeUpdate(query);
		// return (n!=0);
		if (n != 0)
			return true;
		return false;
	}
	public boolean deleteStudentByName(String name) throws SQLException{
		String query = "delete from STUDENT where name = ?";
		java.sql.PreparedStatement stmt = getConnection().prepareStatement(query);
		stmt.setString(1, name);
		int n = stmt.executeUpdate();
		// return (n!=0);
		if (n != 0){
			System.out.println(n+" rows deleted");
		}
		return false;
	}
	public Student findStudentByName(String name) throws SQLException {
		String query = "select * from STUDENT where name='" + name + "'";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
 
		if (resultSet.next()) {
			Student student = new Student();
			student.setId(resultSet.getLong("id"));
			student.setEmail(resultSet.getString("email"));
			student.setName(resultSet.getString("name"));
			return student;
		} else
			return null;
 
	}
	public Student findStudentById(long id) throws SQLException {
		String query = "select * from STUDENT where id='" + id + "'";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
 
		if (resultSet.next()) {
			Student student = new Student();
			student.setId(resultSet.getLong("id"));
			student.setEmail(resultSet.getString("email"));
			student.setName(resultSet.getString("name"));
			return student;
		} else
			return null;
 
	}
	public ArrayList<Student> findAll() throws SQLException {
		String query = "select * from STUDENT ";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
		ArrayList<Student> studentList=new ArrayList<Student>();
		
		while (resultSet.next()) {
			Student student = new Student();
			student.setId(resultSet.getLong("id"));
			student.setEmail(resultSet.getString("email"));
			student.setName(resultSet.getString("name"));
			studentList.add(student);
		} 
		return studentList;
	}
	
	public long generateId() throws SQLException{
		String query = "select max(id) as maxId from STUDENT ";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
		ArrayList<Student> studentList=new ArrayList<Student>();
		if (resultSet.next()) {
			return resultSet.getLong("maxId")+1;
		} else{
			return 0;
		}
	}
	
}
