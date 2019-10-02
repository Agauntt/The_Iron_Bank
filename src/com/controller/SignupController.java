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
import com.iron_bank.model.User;
import com.iron_bank.model.UserDetails;
import com.iron_bank.service.IronBankService;
import com.iron_bank.service.impl.*;

/**
 * Servlet implementation class SignupController
 */
@WebServlet("/signup")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupController() {
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
		System.out.println("Hello from Signup doPost");
		IronBankService service = new IronBankServiceImpl();
		Gson gson = new Gson();
		ServletOutputStream jout = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");
		String requestData = request.getReader().lines().collect(Collectors.joining());
		System.out.println("parsed request");
		System.out.println(requestData);
		UserDetails userDetails = gson.fromJson(requestData, UserDetails.class);
		User user =  gson.fromJson(requestData, User.class);
		userDetails.setUserName(user.getUserName());
		userDetails.setPassWord(user.getPassWord());
		userDetails.setPin(user.getPin());
		RequestDispatcher rd = null;
		try {
			if(service.signUp(userDetails) != null) {
				user.setAcctId(userDetails.getAcctId());
				request.getSession().setAttribute("User", user);
				rd = request.getRequestDispatcher("home");
//				String juser = gson.toJson(userDetails);
//				jout.print(juser);
				rd.forward(request, response);
			} else {
				System.out.println("Hello error");
				user.setAcctId(0);
				String juser = gson.toJson(user);
				jout.print(juser);
			}

		} catch (BusinessException e) {
			System.out.println("Hello error");
			System.out.println(e);
		}
	}

}
