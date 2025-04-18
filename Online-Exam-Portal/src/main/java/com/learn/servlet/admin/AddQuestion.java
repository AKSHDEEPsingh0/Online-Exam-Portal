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
 * Servlet implementation class AddQuestion
 */
@WebServlet("/admin/addQuestion")
public class AddQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddQuestion() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (Authorizer.isAdminAuthorised(session)) {
			request.getRequestDispatcher("/admin/add-question.jsp").include(request, response);
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
			String question = request.getParameter("question");
			String optionA = request.getParameter("optionA");
			String optionB = request.getParameter("optionB");
			String optionC = request.getParameter("optionC");
			String optionD = request.getParameter("optionD");
			String correctOption = request.getParameter("correctOption");
			if (!new QuestionDao().addQuestion(question, optionA, optionB, optionC, optionD, correctOption)) {
				session.setAttribute("error", "Unable to add quiz.");
				request.getRequestDispatcher("/admin/add-question.jsp").include(request, response);
			} else {
				session.setAttribute("info", "Quiz added successfully.");
				response.sendRedirect(request.getContextPath() + "/admin/questions");
			}
		} else {
			session.invalidate();
			session.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}
}
