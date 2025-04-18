package com.learn.servlet.admin;

import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.db.dao.UserDao;
import com.learn.db.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/admin/login")
public class Login extends HttpServlet {

	@Serial
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Display login page
		request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Admin login attempt");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");


		User user = new UserDao().getAdminUser(userName, password);
		System.out.println("Admin login attempt: " + userName);
		System.out.println("Admin password: " + password);

		// ✅ Always invalidate any existing session before starting a new one
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();
		}

		if (user != null) {
			// 🆕 Create a new session for the user
			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/admin/dashboard");
		} else {
			// 🆕 New session for error message (optional, can use request attribute too)
			HttpSession session = request.getSession(true);
			session.setAttribute("error", "Invalid username or password.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}
}
