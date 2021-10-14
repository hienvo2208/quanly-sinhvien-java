package com.vietjack.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.vietjack.core.Category;
import com.vietjack.dbconnection.DBConnection;

public class CategoryDao {
	private Connection conn;

	public Connection getConnection() throws SQLException {
		return DBConnection.getDbCon().getConn();
	}

	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public long calculateRevenueOfCategory(long categoryid) throws SQLException {
		long revenue = 0l;
		String query = "SELECT bs_book.PRICE, bs_order_detail.quantity FROM bs_book, bs_category WHERE  bs_book.id = bs_order_detail.book_id and bs_book.CATEGORY_id = "
				+ categoryid + " and bs_category.id = "+categoryid;
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);

		while (resultSet.next()) {
			revenue += resultSet.getLong("bs_book.PRICE") * resultSet.getLong("bs_order_detail.quantity");
		}
		return revenue;
	}
	
	public ArrayList<Category> findAllCatergory() throws SQLException {
		String query = "select * from bs_category ";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
		ArrayList<Category> categoryList = new ArrayList<Category>();

		while (resultSet.next()) {
			Category category = new Category(resultSet);
			categoryList.add(category);
		}
		return categoryList;
	}

	public Category findCategoryOfBook(long categoryid) throws SQLException {
		String query = "SELECT bs_category.id, bs_category.name FROM bs_book, bs_category WHERE  " + "bs_category.id = "
				+ categoryid;
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
		Category category = new Category();
		if (resultSet.next()) {
			category.setId(resultSet.getLong("bs_category.id"));
			category.setName(resultSet.getString("bs_category.name"));
		}
		return category;
	}

	public boolean addNewCategory(Category category) throws SQLException {
		String query = "insert into bs_category(id,name) values (" + category.getId() + ",'" + category.getName()
				+ "')";
		Statement stmt = getConnection().createStatement();
		int n = stmt.executeUpdate(query);

		if (n != 0)
			return true;
		return false;
	}

	public boolean modifyCategory(Category category) throws SQLException {
		String query = "update bs_category set name='" + category.getName() + "' where id=" + category.getId();
		Statement stmt = getConnection().createStatement();
		int n = stmt.executeUpdate(query);

		if (n != 0)
			return true;
		return false;
	}

	public boolean deleteCategoryByName(String name) throws SQLException {
		String query = "delete from bs_category where name = ?";
		java.sql.PreparedStatement stmt = getConnection().prepareStatement(query);
		stmt.setString(1, name);
		int n = stmt.executeUpdate();

		if (n != 0) {
			System.out.println(n + " rows deleted");
		}
		return false;
	}

	public Category findCategoryByName(String name) throws SQLException {
		String query = "select * from bs_category where name='" + name + "'";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);

		if (resultSet.next()) {
			Category category = new Category(resultSet);
			return category;
		} else
			return null;

	}

	public Category findCategoryById(long id) throws SQLException {
		String query = "select * from bs_category where id='" + id + "'";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);

		if (resultSet.next()) {
			Category category = new Category(resultSet);
			return category;
		} else
			return null;

	}

	public long generateId() throws SQLException {
		String query = "select max(id) as maxid from bs_category ";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);

		if (resultSet.next()) {
			return resultSet.getLong("maxid") + 1;
		} else {
			return 0;
		}
	}
}
