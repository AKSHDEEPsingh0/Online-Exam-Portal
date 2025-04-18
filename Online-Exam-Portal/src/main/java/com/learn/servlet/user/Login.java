package com.learn.servlet.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

import com.learn.db.dao.UserDao;
import com.learn.db.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("username");
		String password = request.getParameter("password");

		UserDao userDao = new UserDao();

		// Always invalidate any old session before starting fresh
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();
		}

		// ✅ Create a new session now
		HttpSession session = request.getSession(true);

		// Admin login
		User adminUser = userDao.getAdminUser(email, password);
		if (adminUser != null) {
			session.setAttribute("user", adminUser);
			System.out.println("✅ Admin login successful");
			response.sendRedirect("admin/dashboard");
			return;
		}

		// Regular user login
		User user = userDao.getUser(email, password);
		if (user != null) {
			session.setAttribute("user", user);
			System.out.println("✅ User login successful");
			response.sendRedirect("dashboard");
			return;
		}

		// Login failed
		System.out.println("❌ Login failed");
		response.sendRedirect("index.jsp");
	}

}

