package com.learn.servlet.user;

import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

import com.learn.db.dao.QuizDao;
import com.learn.db.model.User;
import com.learn.service.QuizQuestionManager;
import com.learn.service.exception.CustomException;
import com.learn.service.exception.InfoMessage;

/**
 * Servlet implementation class SubmitQuestion
 */
@WebServlet("/submit/question")
public class SubmitQuestion extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitQuestion() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		Long quizId = (Long) session.getAttribute("quizId");
		session.setAttribute("quiz", new QuizDao().getQuiz(quizId));
		try {
			System.out.println("In submit question");
			request.setAttribute("question", new QuizQuestionManager().getNextQuestion(user.getId(), quizId));
			request.getRequestDispatcher("/view-quiz.jsp").include(request, response);
		} catch (InfoMessage e) {
			System.out.println("No more questions ");
			session.setAttribute("info", "No questions available.");
			response.sendRedirect(request.getContextPath() + "/dashboard");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		String stQuizId = request.getParameter("quizId");
		Long quizId = Long.parseLong(stQuizId);

		Long quizQuestionId = Long.parseLong(request.getParameter("quizQuestionId"));
		String answer = request.getParameter("answer");

		try {
			request.setAttribute("question",
					new QuizQuestionManager().submitAnswerGetNextQues(user.getId(), quizQuestionId, quizId, answer));
			request.getRequestDispatcher("/view-quiz.jsp").include(request, response);
		} catch (CustomException e) {
			session.setAttribute("error", e.getMessage());
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/leaerBoard/" + quizId + "/");
		} catch (InfoMessage e) {
			session.setAttribute("info", "No more questions");
			response.sendRedirect(request.getContextPath() + "/leaderBoard/" + quizId);
		}
	}
}
