package com.learn.servlet.admin;

import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadSampleFile
 */
@WebServlet("/admin/downloadSampleFile")
public class DownloadSampleFile extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadSampleFile() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contentType = "application/octet-stream";
		byte[] fileBs = getServletContext().getResourceAsStream("sample/Sample.xlsx").readAllBytes();

		response.setHeader("Content-disposition", "attachment;filename=Sample.xlsx");
		response.setHeader("charset", "iso-8859-1");
		response.setContentType(contentType);
		response.setContentLength(fileBs.length);
		response.setStatus(HttpServletResponse.SC_OK);

		try (OutputStream outputStream = response.getOutputStream()) {
			outputStream.write(fileBs, 0, fileBs.length);
			outputStream.flush();
			response.flushBuffer();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
