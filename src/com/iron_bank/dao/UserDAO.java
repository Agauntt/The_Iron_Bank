package com.iron_bank.dao;

import com.iron_bank.exceptions.BusinessException;
import com.iron_bank.model.User;
import com.iron_bank.model.UserDetails;

public interface UserDAO {

	public UserDetails registerDetails(UserDetails uDetails) throws BusinessException;
	public User authUser(User user) throws BusinessException;
	public User authAdminUser(User user) throws BusinessException;
	public UserDetails displayDetails(long userID) throws BusinessException;
	public boolean checkUnique(UserDetails udetails) throws BusinessException;
	public boolean resetPassword(UserDetails uDetails) throws BusinessException;
}
