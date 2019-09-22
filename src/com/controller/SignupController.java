package com.controller;

import java.io.IOException;
import java.util.stream.Collectors;

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
import com.iron_bank.service.impl.IronBankServiceImpl;

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
		System.out.println("raw request");
		System.out.println(request);
		String requestData = request.getReader().lines().collect(Collectors.joining());
		System.out.println("parsed request");
		System.out.println(requestData);
		UserDetails userDetails = gson.fromJson(requestData, UserDetails.class);
		User user =  gson.fromJson(requestData, User.class);
		userDetails.setUserName(user.getUserName());
		userDetails.setPassWord(user.getPassWord());
		userDetails.setPin(user.getPin());
		try {
			service.signUp(userDetails);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
