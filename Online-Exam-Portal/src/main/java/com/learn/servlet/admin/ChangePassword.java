package com.learn.servlet.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.db.dao.UserDao;
import com.learn.db.model.User;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/admin/changePassword")
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
		if (Authorizer.isAdminAuthorised(session)) {
			request.getRequestDispatcher("/admin/change-password.jsp").include(request, response);
		} else {
			session.invalidate();
			session.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (Authorizer.isAdminAuthorised(session)) {
			User user = (User) session.getAttribute("user");
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newPassword");
			String retypePassword = request.getParameter("retypePassword");
			UserDao userDao = new UserDao();

			if (!newPassword.equals(retypePassword)) {
				session.setAttribute("error", "New Password and Retype Password should be the same.");
				request.getRequestDispatcher("/admin/change-password.jsp").include(request, response);
			} else {
				String currentPassword = userDao.getPassword(user.getId());
				if (currentPassword == null) {
					session.setAttribute("error", "User does not exist, please login again.");
					response.sendRedirect(request.getContextPath() + "/admin/login");
				} else if (!currentPassword.equals(password)) {
					session.setAttribute("error", "Invalid Password.");
					request.getRequestDispatcher("/admin/change-password.jsp").include(request, response);
				} else {
					if (userDao.updatePassword(user.getId(), newPassword)) {
						session.setAttribute("info",
								"Password has been changed successfully, you need to login again.");
						response.sendRedirect(request.getContextPath() + "/admin/login");
					} else {
						session.setAttribute("error", "Failed to update password. Please try again.");
						request.getRequestDispatcher("/admin/change-password.jsp").include(request, response);
					}
				}
			}
		} else {
			session.invalidate();
			session.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}
}
