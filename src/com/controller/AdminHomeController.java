package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

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
 * Servlet implementation class HomeController
 */
@WebServlet("/adminHome")
public class AdminHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminHomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			IronBankService service = new IronBankServiceImpl();
			Gson gson = new Gson();
			ServletOutputStream jout = response.getOutputStream();
			response.setContentType("application/json;charset=UTF-8");
			System.out.println("Admin Home servlet doGet");		
			User user = new User();
			user .setAcctId((long) request.getSession().getAttribute("Admin"));
			System.out.println(user);
			String juser = gson.toJson(user);
			jout.print(juser);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		IronBankService service = new IronBankServiceImpl();
//		Gson gson = new Gson();
//		ServletOutputStream jout = response.getOutputStream();
//		response.setContentType("application/json;charset=UTF-8");
//		System.out.println("Home servlet doGet");		
//		User user = (User) request.getSession().getAttribute("User");
////		System.out.println(user);
//		try {
////			System.out.println("User ID: "+user.getAcctId());
//			UserDetails udetails = service.displayDetails(user.getAcctId());
////			System.out.println(udetails);
//			String juser = gson.toJson(udetails);
////			System.out.println("Printing juser...");
////			System.out.println(juser);
//			jout.print(juser);
////			jout.print("addition info");
//		} catch (BusinessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
