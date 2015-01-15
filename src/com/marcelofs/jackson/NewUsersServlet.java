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
import com.marcelofs.jackson.persistence.data.NewUsersDay;

public class NewUsersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public final DateTimeZone eRepTimeZone = DateTimeZone
			.forID("America/Los_Angeles");

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String from = req.getParameter("from");

		if (from == null || from.trim().isEmpty()
				|| !from.trim().matches("\\d{4}-?\\d{2}-?\\d{2}")) {

			LocalDate date = new LocalDate(eRepTimeZone).minus(Months.THREE);
			from = date.toString("yyyyMMdd");
		}

		JSONArray response = new JSONArray();
		try (CloseablePM pm = PMF.getNewPersistenceManager()) {
			List<NewUsersDay> users = new NewUsersDays(pm).getFrom(from.trim());
			response.addAll(users);
		}
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().write(response.toString());
	}
}