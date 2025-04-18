package com.learn.servlet.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.db.dao.QuestionDao;

/**
 * Servlet implementation class ViewQuestion
 */
@WebServlet("/admin/viewQuestion/*")
public class ViewQuestion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewQuestion() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null && Authorizer.isAdminAuthorised(session)) {
			try {
				Long questionId = Long.parseLong(request.getPathInfo().substring(1));
				request.setAttribute("question", new QuestionDao().getQuestion(questionId));
				request.getRequestDispatcher("/admin/view-question.jsp").include(request, response);
			} catch (NumberFormatException | NullPointerException e) {
				session.setAttribute("error", "Invalid question ID.");
				response.sendRedirect(request.getContextPath() + "/admin/questions");
			}
		} else {
			if (session != null) {
				session.invalidate();
			}
			request.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}
}
