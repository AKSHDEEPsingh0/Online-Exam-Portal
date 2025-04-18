package com.learn.servlet.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.db.dao.QuizDao;
import com.learn.db.dao.QuizQuestionDao;

/**
 * Servlet implementation class ViewQuiz
 */
@WebServlet("/admin/viewQuiz/*")
public class ViewQuiz extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewQuiz() {
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
				Long quizId = Long.parseLong(request.getPathInfo().substring(1));
				request.setAttribute("quiz", new QuizDao().getQuiz(quizId));
				request.setAttribute("questionList", new QuizQuestionDao().getQuestions(quizId));
				request.getRequestDispatcher("/admin/view-quiz.jsp").include(request, response);
			} catch (NumberFormatException | NullPointerException e) {
				session.setAttribute("error", "Invalid quiz ID.");
				response.sendRedirect(request.getContextPath() + "/admin/quizList");
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
