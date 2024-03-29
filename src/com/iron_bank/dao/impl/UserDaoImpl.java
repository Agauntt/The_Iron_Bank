package com.iron_bank.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.dbutil.OracleConnection;
import com.iron_bank.dao.UserDAO;
import com.iron_bank.exceptions.BusinessException;
import com.iron_bank.model.User;
import com.iron_bank.model.UserDetails;

public class UserDaoImpl implements UserDAO{

	// This method takes in a UserDetails object and passes it through the register_User procedure to 
	// create a new user account with a sequentially generated account number 
	@Override
	public UserDetails registerDetails(UserDetails uDetails) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()){
			String sql = "{call register_User(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(2, uDetails.getUserName());
			callableStatement.setString(3, uDetails.getPassWord());
			callableStatement.setInt(4,  uDetails.getPin());
			callableStatement.setString(5,  uDetails.getFirstName());
			callableStatement.setString(6,  uDetails.getLastName());
			callableStatement.setString(7, uDetails.getContact());
			callableStatement.setString(8,  uDetails.getEmail());
			callableStatement.setString(9,  uDetails.getAddress());
			callableStatement.setString(10,  uDetails.getCity());
			callableStatement.setString(11,  uDetails.getState());
			callableStatement.setInt(12,  uDetails.getZip());
			callableStatement.setDate(13, new java.sql.Date(uDetails.getDob().getTime()));
			callableStatement.setString(14, uDetails.getSsn());
			callableStatement.setString(15, uDetails.getSq());
			callableStatement.registerOutParameter(1, java.sql.Types.NUMERIC);
			
			callableStatement.execute();
			uDetails.setAcctId(callableStatement.getInt(1));
		} catch(SQLIntegrityConstraintViolationException e) {
			throw new BusinessException("Email account is already registered, please use another one. If you feel you "
					+ "have reached this message in error, please contact customer service");
			
		}
		catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
			throw new BusinessException("Internal error occured");
		} 
		return uDetails;
	}

	@Override
	public boolean checkUnique(UserDetails uDetails) throws BusinessException {
		boolean b = false;
		try(Connection connection = OracleConnection.getConnection()) {
				String sql = "SELECT * FROM user_info where email = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, uDetails.getEmail());
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					b = true;
				}
		} catch (ClassNotFoundException | SQLException e) {
//			throw new BusinessException("Email is already registered...Please try again");
		}
		
		return b;
	}

	// Takes in a user object that is partially generated by the user attempting to log in, if the credentials match an existing user,
	// the rest of the object is retrieved from the DB and passed back, allowing the user to access account features within the main
	// menu
	@Override
	public User authUser(User user) throws BusinessException {
		System.out.println("Dao reached: " + user);
		try(Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT acct_id, pin FROM users WHERE username = ? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassWord());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user.setAcctId(resultSet.getLong("acct_id"));
				user.setPin(resultSet.getInt("pin"));
			} else {
				System.out.println("User not found");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println(user);
		return user;
	}


	@Override
	public UserDetails displayDetails(long userID) throws BusinessException {
		UserDetails uDetails = new UserDetails();
		try(Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT * FROM user_info where user_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, userID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				uDetails.setAcctId(userID);
				uDetails.setFirstName(resultSet.getString("first_name"));
				uDetails.setLastName(resultSet.getString("last_name"));
				uDetails.setAddress(resultSet.getString("address"));
				uDetails.setCity(resultSet.getString("city"));
				uDetails.setState(resultSet.getString("state"));
				uDetails.setSsn(resultSet.getString("ssn"));
				uDetails.setDob(resultSet.getDate("dob"));
				uDetails.setContact(resultSet.getString("contact"));
				uDetails.setEmail(resultSet.getString("email"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured...Please try again later");
		}
		return uDetails;
	}

	@Override
	public boolean resetPassword(UserDetails uDetails) throws BusinessException {
		boolean b = false;
		System.out.println(uDetails);
		try(Connection connection = OracleConnection.getConnection()) {
			String sql = "Select *  FROM user_info where email = ? and sq = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, uDetails.getEmail());
			preparedStatement.setString(2, uDetails.getSq());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("User found");
				sql = "UPDATE users SET password = ? WHERE username = ? ";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, uDetails.getPassWord());
				preparedStatement.setString(2, uDetails.getEmail());
				int update = preparedStatement.executeUpdate();
				System.out.println(update);
				} 
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				throw new BusinessException("Internal error occured...Please try again later");
			}
		return b;
	}

	public User authAdminUser(User user) {
		System.out.println("Admin Dao reached: " + user);
		try(Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT adminId FROM admins WHERE username = ? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassWord());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user.setAcctId(resultSet.getLong("adminId"));
			} else {
				System.out.println("User not found");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println(user);
		return user;
	}

}
