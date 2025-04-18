package com.learn.servlet.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

import com.learn.servlet.admin.Authorizer;

/**
 * Servlet implementation class EditProfile
 */
@WebServlet("/edit/profile")
public class EditProfile extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditProfile() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (Authorizer.isUserAuthorised(session)) {
			request.getRequestDispatcher("/edit-profile.jsp").include(request, response);
		} else {
			if (session != null) {
				session.invalidate();
			}
			request.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}
}
