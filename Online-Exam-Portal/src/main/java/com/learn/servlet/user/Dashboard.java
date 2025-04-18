package com.learn.servlet.user;

import com.learn.db.model.Quiz;
import com.learn.db.dao.QuizDao;
import com.learn.servlet.admin.Authorizer;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	@Serial
	private static final long serialVersionUID = 1L;

	public Dashboard() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false); // Don't create new session

		if (session != null && Authorizer.isUserAuthorised(session)) {
			System.out.println("✅ User is authorized.");

			// Fetch quizzes from the database
			List<Quiz> quizList = null;

			try {
				QuizDao quizDao = new QuizDao();
				quizList = quizDao.getAllQuiz();

				if (quizList == null || quizList.isEmpty()) {
					quizList = new ArrayList<>(); // Initialize empty list
				} else {
					System.out.println("✅ Quizzes fetched: " + quizList.size());
				}

			} catch (Exception e) {
				e.printStackTrace();

				request.setAttribute("error", "Unable to load quizzes. Please try again later.");
				RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
				rd.forward(request, response);
				return;
			}

			// Set the quiz list to request
			request.setAttribute("quizList", quizList);

			// Forward to dashboard.jsp
			RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
			rd.forward(request, response);

		} else {
			// Unauthorized or no session
			System.out.println("❌ Unauthorized access. Redirecting to login.");

			if (session != null) {
				session.invalidate();
			}

			response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
