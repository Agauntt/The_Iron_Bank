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
import com.iron_bank.model.User;
import com.iron_bank.model.UserDetails;
import com.iron_bank.service.IronBankService;
import com.iron_bank.service.impl.IronBankServiceImpl;

/**
 * Servlet implementation class ResetPassword
 */
@WebServlet("/resetpw")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello from reset post");
		IronBankService service = new IronBankServiceImpl();
		Gson gson = new Gson();
		ServletOutputStream jout = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");
		String requestData = request.getReader().lines().collect(Collectors.joining());
		System.out.println("Raw request");
		System.out.println(requestData);
		UserDetails user = gson.fromJson(requestData, UserDetails.class);
		RequestDispatcher rd = null;
		try {
			System.out.println(user.toString());
			if(service.resetPassword(user)) {
				System.out.println("Success: " + user);
				request.getSession().setAttribute("User", user);
				rd = request.getRequestDispatcher("home");
				String juser = gson.toJson(user);
				System.out.println(juser);
				rd.forward(request, response);
			} else {
				String juser = gson.toJson(user);
				jout.print(juser);
			}
		} catch(Exception e){
			System.out.println(e);
		}
	}
}
