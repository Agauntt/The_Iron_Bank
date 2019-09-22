package com.iron_bank.service.impl;

import java.security.MessageDigest;

//import java.security.MessageDigest;

import com.iron_bank.dao.AccountDAO;
import com.iron_bank.dao.UserDAO;
import com.iron_bank.dao.impl.AccountDaoImpl;
import com.iron_bank.dao.impl.UserDaoImpl;
import com.iron_bank.exceptions.BusinessException;
import com.iron_bank.model.Account;
import com.iron_bank.model.User;
import com.iron_bank.model.UserDetails;
import com.iron_bank.service.IronBankService;

public class IronBankServiceImpl implements IronBankService {

	private AccountDAO acctDAO;
	private UserDAO uDAO;

	@Override
	public String hashBrowns(String password) {
			StringBuffer message=new StringBuffer();
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				byte[] hash = md.digest(password.getBytes("UTF-8"));

				for (byte w : hash) {
					message.append(String.format("%02x", w));
				}
			}catch (Exception e) {
				System.out.println(e);
			}
		return message.toString();
	}

	@Override
	public User login(User user) throws BusinessException {
		try {
			UserDaoImpl dao = new UserDaoImpl();
			System.out.println("service layer: "+user);
			user = dao.authUser(user);
			if(user.getAcctId()!=0) {
				return user;
			} 
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}

	@Override
	public UserDetails signUp(UserDetails uDetails) {
		UserDaoImpl dao = new UserDaoImpl();
		uDetails.setPassWord(hashBrowns(uDetails.getPassWord()));
		try {
			dao.registerDetails(uDetails);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account createChecking(Account account) throws BusinessException {
		
		return null;
	}

	@Override
	public UserDetails displayDetails(long acctId) throws BusinessException {
		UserDetails uDetails = new UserDetails();
		UserDAO dao = getUserDao();
		uDetails = dao.displayDetails(acctId);
		return uDetails;
	}

	@Override
	public Account getAccountById(long acctId) throws BusinessException {
		//
		return null;
	}
	
	public AccountDAO getAccountDao() {
		if(acctDAO==null) {
			acctDAO=new AccountDaoImpl();
		}
		return acctDAO;
	}
	
	public UserDAO getUserDao() {
		if(uDAO==null) {
			uDAO = new UserDaoImpl();
		}
		return uDAO;
	}

	@Override
	public boolean checkEmail(UserDetails uDetails) throws BusinessException {
		UserDaoImpl dao = new UserDaoImpl();
		boolean b = dao.checkUnique(uDetails);
//		if (b) {
//			throw new BusinessException("Email account is already registered");
//		} 
//		System.out.print(b);
		return b;
	}

	@Override
	public boolean resetPassword(UserDetails uDetails) throws BusinessException {
		boolean b = false;
		UserDaoImpl dao = new UserDaoImpl();
		if(dao.resetPassword(uDetails)) {
			b = true;
		}
		return b;
	}
	
}

