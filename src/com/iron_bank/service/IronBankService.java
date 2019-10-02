package com.iron_bank.service;

import java.util.List;

import com.iron_bank.exceptions.BusinessException;
import com.iron_bank.model.Account;
import com.iron_bank.model.User;
import com.iron_bank.model.UserDetails;


public interface IronBankService {

	public String hashBrowns(String password) throws BusinessException;
	public UserDetails signUp(UserDetails uDetails) throws BusinessException;
	public User login(User user) throws BusinessException;
	public Account createChecking(Account account) throws BusinessException;
	public Account getAccountById(long acctId) throws BusinessException;
	public boolean checkEmail(UserDetails uDetails) throws BusinessException;
	public UserDetails displayDetails(long acctId) throws BusinessException;
	public boolean resetPassword(UserDetails uDetails) throws BusinessException;
	public List<Account> displayAccounts(long acctId) throws BusinessException;
	public Account makeTransaction(long acctId, long ownerId, double trans) throws BusinessException;
	public User adminLogin(User user) throws BusinessException;
}
