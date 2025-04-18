package com.learn.servlet.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.db.dao.UserDao;
import com.learn.db.model.User;
import com.learn.servlet.admin.Authorizer;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/changePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null && Authorizer.isUserAuthorised(session)) {
			request.getRequestDispatcher("/change-password.jsp").include(request, response);
		} else {
			if (session != null) {
				session.invalidate();
			}
			request.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null && Authorizer.isUserAuthorised(session)) {
			User user = (User) session.getAttribute("user");
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newPassword");
			String retypePassword = request.getParameter("retypePassword");
			UserDao userDao = new UserDao();

			if (newPassword == null || retypePassword == null || password == null) {
				session.setAttribute("error", "All fields are required.");
				request.getRequestDispatcher("/change-password.jsp").include(request, response);
				return;
			}

			if (!newPassword.equals(retypePassword)) {
				session.setAttribute("error", "New Password and Retype Password must match.");
				request.getRequestDispatcher("/change-password.jsp").include(request, response);
				return;
			}

			String currentPassword = userDao.getPassword(user.getId());
			if (currentPassword == null) {
				session.invalidate();
				session.setAttribute("error", "User does not exist. Please login again.");
				response.sendRedirect(request.getContextPath() + "/login");
			} else if (!currentPassword.equals(password)) {
				session.setAttribute("error", "Invalid current password.");
				request.getRequestDispatcher("/change-password.jsp").include(request, response);
			} else {
				if (userDao.updatePassword(user.getId(), newPassword)) {
					session.setAttribute("info", "Password changed successfully. Please login again.");
					session.invalidate();
					response.sendRedirect(request.getContextPath() + "/login");
				} else {
					session.setAttribute("error", "Password change failed. Please try again.");
					request.getRequestDispatcher("/change-password.jsp").include(request, response);
				}
			}
		} else {
			if (session != null) {
				session.invalidate();
			}
			request.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}
}
