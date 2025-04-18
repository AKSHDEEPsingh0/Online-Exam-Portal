package com.learn.servlet.admin;

import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.db.dao.QuestionDao;

/**
 * Servlet implementation class GetAllQuestion
 */
@WebServlet("/admin/questions")
public class GetAllQuestion extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllQuestion() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (Authorizer.isAdminAuthorised(session)) {
			session.setAttribute("questionList", new QuestionDao().getAllQuestion());
			request.getRequestDispatcher("/admin/questions.jsp").include(request, response);
		} else {
			session.invalidate();
			session.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}
}
