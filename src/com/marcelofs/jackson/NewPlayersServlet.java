package com.marcelofs.jackson;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.json.simple.JSONArray;

import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.dao.NewUsersDays;
import com.marcelofs.jackson.persistence.dao.Users;
import com.marcelofs.jackson.persistence.data.NewUsersDay;

public class NewPlayersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public final DateTimeZone eRepTimeZone = DateTimeZone
			.forID("America/Los_Angeles");

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		JSONArray response = new JSONArray();

		try (CloseablePM pm = PMF.getNewPersistenceManager()) {

			boolean hasLicence = new Users(pm).hasLicence();

			LocalDate from;
			LocalDate today = new LocalDate(eRepTimeZone);
			if (hasLicence) {
				String param = req.getParameter("from");
				if (param != null && !param.trim().isEmpty()
						&& param.trim().matches("\\d{4}-?\\d{2}-?\\d{2}")) {
					from = new LocalDate(param, eRepTimeZone);
				} else {
					from = today.minus(Months.THREE);;
				}
			} else {
				from = today.minus(Months.ONE);
			}
			NewUsersDays dao = new NewUsersDays(pm);
			List<NewUsersDay> players = dao.getFrom(from, hasLicence ? today : today.minusDays(2));
			response.addAll(players);
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().write(response.toString());
	}
}