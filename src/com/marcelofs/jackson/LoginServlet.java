package com.marcelofs.jackson;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");

		UserService userService = UserServiceFactory.getUserService();
		if (userService.getCurrentUser() == null) {
			resp.sendRedirect(userService.createLoginURL("/"));
		} else {
			resp.sendRedirect(userService.createLogoutURL("/"));
		}
	}
}