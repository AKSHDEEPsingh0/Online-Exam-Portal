package com.learn.servlet.admin;

import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.db.dao.QuestionDao;
import com.learn.db.dao.QuizDao;
import com.learn.db.dao.UserDao;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/admin/dashboard")
public class Dashboard extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (Authorizer.isAdminAuthorised(session)) {
			session.setAttribute("quizList", new QuizDao().getAllQuiz());
			session.setAttribute("totalUser", new UserDao().totalCount());
			session.setAttribute("totalQuiz", new QuizDao().totalCount());
			session.setAttribute("totalQuestion", new QuestionDao().totalCount());
			request.getRequestDispatcher("/admin/dashboard.jsp").include(request, response);
		} else {
			if (session != null) {
				session.invalidate(); // ❌ Invalidate current session
			}


			// ✅ Start a new session safely
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute("error", "Unauthorised request.");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}
	}

}
