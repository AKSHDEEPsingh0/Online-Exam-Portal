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
import com.learn.db.dao.UserQuizQuestionAnswerDao;

/**
 * Servlet implementation class LeaderBoard
 */
@WebServlet("/leaderBoard/*")
public class LeaderBoard extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaderBoard() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println("In leaderboard");

		Long quizId = 0L;
		try {
			quizId = Long.parseLong(request.getPathInfo().substring(1));
		} catch (NumberFormatException n) {
			request.getRequestDispatcher("/leader-board.jsp").include(request, response);
			return;
		}

		request.setAttribute("quiz", new QuizDao().getQuiz(quizId));
		System.out.println("QuizDao object created");
		request.setAttribute("userScores", new UserQuizQuestionAnswerDao().getLeaderBoard(quizId));
		System.out.println("UserQuizQuestionAnswerDao object created");
		request.getRequestDispatcher("/leader-board.jsp").include(request, response);
	}
}
