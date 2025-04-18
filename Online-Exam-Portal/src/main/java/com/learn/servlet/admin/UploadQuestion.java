package com.learn.servlet.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.learn.db.dao.QuestionDao;
import com.learn.utility.ExcelReader;

/**
 * Servlet implementation class UploadQuiz
 */
@WebServlet("/admin/uploadQuestion")
@MultipartConfig
public class UploadQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadQuestion() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (Authorizer.isAdminAuthorised(session)) {
			request.getRequestDispatcher("/admin/upload-question.jsp").include(request, response);
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
			Part attachedFile = request.getPart("attachedFile");
			InputStream inputStream = attachedFile.getInputStream();
			ExcelReader excelReader = new ExcelReader();
			List<Long> questionIds = new QuestionDao().addQuestion(excelReader.getQuestion(inputStream));
			if (questionIds == null || questionIds.isEmpty()) {
				session.setAttribute("error", "Unable to add quiz.");
				request.getRequestDispatcher("/admin/upload-question.jsp").include(request, response);
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
