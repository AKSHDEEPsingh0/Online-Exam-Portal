package com.learn.servlet.user;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Apply this filter to all URLs that need user authentication
@WebFilter({"/dashboard", "/start/quiz/*", "/submit/question"})
public class LoginFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {}

	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);

		// If session is missing or "user" attribute is not present, redirect to login
		if (session == null || session.getAttribute("user") == null) {
			System.out.println("🚫 User not logged in. Redirecting to login page.");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		// Otherwise, continue to the requested resource
		System.out.println("✅ User authenticated. Proceeding.");
		chain.doFilter(request, response);
	}
}
