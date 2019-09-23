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
import com.iron_bank.model.Transaction;
import com.iron_bank.model.User;
import com.iron_bank.service.IronBankService;
import com.iron_bank.service.impl.IronBankServiceImpl;

/**
 * Servlet implementation class TransactionController
 */
@WebServlet("/transaction")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Transaction doPost");
		IronBankService service = new IronBankServiceImpl();
		Gson gson = new Gson();
		ServletOutputStream out = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");
		User user = (User) request.getSession().getAttribute("User");
		String requestData = request.getReader().lines().collect(Collectors.joining());
		System.out.println("Raw request");
		System.out.println(requestData);
		Account acct = gson.fromJson(requestData, Account.class);
		Transaction t = gson.fromJson(requestData, Transaction.class);
		RequestDispatcher rd = null;
		System.out.println(acct);
		System.out.println(t);
		try {
			acct = service.getAccountById(acct.getAcctId());
			double b = acct.getBalance() + t.getAmount();
			acct.setBalance(b);
			service.makeTransaction(acct.getAcctId(), user.getAcctId(), acct.getBalance());
			out.print(acct.toString());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
