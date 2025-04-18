package com.learn.servlet.user;

import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

import com.learn.db.model.User;

/**
 * Servlet implementation class StartQuiz
 */
@WebServlet("/start/quiz/*")
public class StartQuiz extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println("Hello World");
		System.out.println("PathInfo: " + request.getPathInfo());

		if (session == null) {
			System.out.println("⚠️ Session is null");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		Long quizId = Long.parseLong(request.getPathInfo().substring(1));
		System.out.println("✅ Quiz ID Parsed: " + quizId);

		session.setAttribute("quizId", quizId);
		System.out.println("Redirecting to submit/question");
		response.sendRedirect(request.getContextPath() + "/submit/question");
	}


}
