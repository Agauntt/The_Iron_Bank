package com.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.iron_bank.exceptions.BusinessException;
import com.iron_bank.model.Account;
import com.iron_bank.model.User;
import com.iron_bank.service.IronBankService;
import com.iron_bank.service.impl.IronBankServiceImpl;

/**
 * Servlet implementation class CreateAccount
 */
@WebServlet("/create")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello from create post");
		IronBankService service = new IronBankServiceImpl();
		Gson gson = new Gson();
		ServletOutputStream jout = response.getOutputStream();
//		response.setContentType("application/json;charset=UTF-8");
		String requestData = request.getReader().lines().collect(Collectors.joining());
		RequestDispatcher rd = null;
		User user = (User) request.getSession().getAttribute("User");
		Account acct = gson.fromJson(requestData, Account.class);
		System.out.println(user);
		acct.setOwnerId(user.getAcctId());
		System.out.println(acct);
		try {
			acct = service.createChecking(acct);
			String jacct = gson.toJson(acct);
			jout.print(jacct);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
