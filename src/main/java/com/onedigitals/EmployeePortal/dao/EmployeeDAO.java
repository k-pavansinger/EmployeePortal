package com.onedigitals.EmployeePortal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.onedigitals.EmployeePortal.dbconnection.ConnectionPool;
import com.onedigitals.EmployeePortal.models.EmployeeModel;

public class EmployeeDAO {
	private Connection connection;

	public void addEmployee(EmployeeModel employee) {
		String sql = "INSERT INTO employees (first_name, last_name, role, email, gender, dob, start_date, address, password, leave_balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = ConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, employee.getFirstName());
			statement.setString(2, employee.getLastName());
			statement.setString(3, employee.getRole());
			statement.setString(4, employee.getEmail());
			statement.setString(5, employee.getGender());
			statement.setDate(6, new java.sql.Date(employee.getDob().getTime()));
			statement.setDate(7, new java.sql.Date(employee.getStartDate().getTime()));
			statement.setString(8, employee.getAddress());

			// Hash the password using bcrypt
			String hashedPassword = BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt());
			statement.setString(9, hashedPassword);

			statement.setInt(10, employee.getLeaveBalance());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public EmployeeModel getEmployee(String emailId) {
		String sql = "SELECT * FROM employees WHERE email = ?";
		try (Connection connection = ConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, emailId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				EmployeeModel employee = new EmployeeModel();
				employee.setId(resultSet.getInt("id"));
				employee.setFirstName(resultSet.getString("first_name"));
				employee.setLastName(resultSet.getString("last_name"));
				employee.setRole(resultSet.getString("role"));
				employee.setEmail(resultSet.getString("email"));
				employee.setGender(resultSet.getString("gender"));
				employee.setDob(resultSet.getDate("dob"));
				employee.setStartDate(resultSet.getDate("start_date"));
				employee.setAddress(resultSet.getString("address"));
				employee.setPassword(resultSet.getString("password"));
				employee.setLeaveBalance(resultSet.getInt("leave_balance"));
				return employee;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
