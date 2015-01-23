package com.marcelofs.jackson;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.dao.Users;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");

		UserService userService = UserServiceFactory.getUserService();
		if (userService.getCurrentUser() == null) {
			resp.sendRedirect(userService.createLoginURL("/login?create"));
			return;
		}
		if ("create".equals(req.getQueryString())) {
			create();
			resp.sendRedirect("/");
			return;
		}

		resp.sendRedirect(userService.createLogoutURL("/"));
	}

	private void create() {
		User gUser = UserServiceFactory.getUserService().getCurrentUser();
		try (CloseablePM pm = PMF.getNewPersistenceManager()) {
			Users dao = new Users(pm);
			try {
				dao.get(gUser);
			} catch (JDOObjectNotFoundException e) {
				dao.add(gUser);
				logger.log(Level.WARNING, "Created new user {0}", gUser.getEmail());
			}
		}
	}
}