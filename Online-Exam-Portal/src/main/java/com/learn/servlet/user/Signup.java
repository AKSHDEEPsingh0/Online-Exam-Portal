package com.learn.servlet.user;

import java.io.IOException;

import com.learn.db.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import com.learn.db.dao.UserDao;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/signup")
public class Signup extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Signup() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("sign-up.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String emailId = request.getParameter("emailId");
		String mobileNumber = request.getParameter("mobileNumber");
		String password = request.getParameter("password");

		UserDao userDao = new UserDao();
		if (userDao.createUser(firstName, lastName, emailId, mobileNumber, password)) {
			// Get the created user from the database
			User user = userDao.getUser(emailId, password);

			if (user != null) {
				// Set session attributes for the logged-in user
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("login", true);

				// Redirect to the dashboard after successful signup
				response.sendRedirect(request.getContextPath() + "/dashboard");
			} else {
				request.setAttribute("info", "Account created, but login failed. Please try logging in.");
				request.getRequestDispatcher("login.jsp").include(request, response);
			}
		} else {
			request.setAttribute("info", "Unable to create account, Please contact the admin.");
			request.getRequestDispatcher("sign-up.jsp").include(request, response);
		}
	}


}
