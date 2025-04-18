package com.learn.servlet.admin;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.db.dao.QuizDao;
import com.learn.db.dao.QuizQuestionDao;

/**
 * Servlet implementation class AddQuizQuestion
 */
@WebServlet("/admin/addQuizQuestion/*")
public class AddQuizQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddQuizQuestion() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (Authorizer.isAdminAuthorised(session)) {
			Long quizId = Long.parseLong(request.getPathInfo().substring(1));
			request.setAttribute("quiz", new QuizDao().getQuiz(quizId));
			request.setAttribute("questionList", new QuizQuestionDao().getQuestionsNotAssignedToQuiz(quizId));
			request.getRequestDispatcher("/admin/add-quiz-question.jsp").include(request, response);
		} else {
			session.invalidate();
			session.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (Authorizer.isAdminAuthorised(session)) {
			Long quizId = Long.parseLong(request.getPathInfo().substring(1));
			String[] questions = request.getParameterValues("questionId");
			List<Long> questionIds = questions != null
					? Stream.of(questions).map(Long::valueOf).collect(Collectors.toList())
					: Collections.emptyList();

			if (new QuizQuestionDao().addQuizQuestion(quizId, questionIds)) {
				session.setAttribute("info", "Question added successfully.");
				response.sendRedirect(request.getContextPath() + "/admin/viewQuiz/" + quizId);
			} else {
				session.setAttribute("error", "Unable to add Question.");
				request.getRequestDispatcher("/admin/viewQuiz/" + quizId).include(request, response);
			}
		} else {
			session.invalidate();
			session.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}
}
