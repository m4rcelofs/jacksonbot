package com.marcelofs.jackson;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTimeZone;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.dao.Users;
import com.marcelofs.jackson.persistence.data.User;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public final DateTimeZone eRepTimeZone = DateTimeZone
			.forID("America/Los_Angeles");

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		Integer sent = null;
		try {
			JSONObject incoming = (JSONObject) JSONValue.parse(req.getReader());
			sent = Integer.parseInt(incoming.get("messagesSent").toString());
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		try (CloseablePM pm = PMF.getNewPersistenceManager()) {
			Users dao = new Users(pm);
			User user = dao.getCurrent();
			user.setMessagesSent(sent);
		}
		
		resp.getWriter().write("{\"messagesSent\": " + sent + "}");
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String message = null;
		try {
			JSONObject incoming = (JSONObject) JSONValue.parse(req.getReader());
			message = incoming.get("message").toString();
		} catch (Exception e) {}

		if(message == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
				
		String sender = UserServiceFactory.getUserService().getCurrentUser()
				.getEmail();

		Mailer m = new Mailer();
		m.mail("Feedback de " + sender + " recebido", message,
				m.new MailAddress(sender), m.new MailAddress("admins"));
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");

		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User gUser = userService
				.getCurrentUser();

		if (gUser == null) {
			resp.sendError(SC_UNAUTHORIZED);
		} else {
			JSONObject response = new JSONObject();

			try (CloseablePM pm = PMF.getNewPersistenceManager()) {
				Users dao = new Users(pm);
				User user = dao.getCurrent();
				response.put("nick", user.getNick());
				response.put("id", user.getId());
				response.put("party", user.getParty());
				response.put("mu", user.getMu());
				response.put("messagesSent", user.getMessagesSent());
				response.put("hasLicence", dao.hasLicence(user));
				response.put("hasLicence", true); // FIXME
			} catch (JDOObjectNotFoundException e) {
				resp.sendRedirect("/login?create");
			}

			response.put("email", gUser.getEmail());
			resp.getWriter().write(response.toString());
		}
	}
}