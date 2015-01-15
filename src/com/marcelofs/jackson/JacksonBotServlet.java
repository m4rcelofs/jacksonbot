package com.marcelofs.jackson;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withPayload;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.dao.Configs;
import com.marcelofs.jackson.tasks.GetNewUsersTask;

public class JacksonBotServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(JacksonBotServlet.class
			.getName());

	public final DateTimeZone eRepTimeZone = DateTimeZone
			.forID("America/Los_Angeles");

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String from = req.getParameter("from");
		if (from == null || from.trim().isEmpty()
				|| !from.matches("\\d{4}-\\d{2}-\\d{2}")) {
			getYesterday();
		} else {
			getFrom(from);
		}
	}

	private void getYesterday() {
		try (CloseablePM pm = PMF.getNewPersistenceManager()) {
			Boolean enabled = new Configs(pm).getDefault().getEnabled();
			LocalDate yesterday = new LocalDate(eRepTimeZone).minus(Days.ONE);
			if (enabled != null && enabled)
				addToQueue(yesterday, true);
		}
	}

	private void getFrom(String date) {
		LocalDate min = LocalDate.parse(date);
		LocalDate max = new LocalDate(eRepTimeZone);
		while (min.isBefore(max)) {
			addToQueue(min, false);
			min = min.plus(Days.ONE);
		}
	}

	private void addToQueue(LocalDate date, boolean sendMessage) {
		logger.log(Level.WARNING, "adding " + date + ", sendMessage="
				+ sendMessage + " to queue");
		DeferredTask task = new GetNewUsersTask(date.toString("yyyy-MM-dd"),
				sendMessage);
		Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOptions = withPayload(task).taskName(
				"GetNewUsersTask_" + date.toString("yyyyMMdd"));
		queue.addAsync(taskOptions);
	}
}