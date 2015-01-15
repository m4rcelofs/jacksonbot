package com.marcelofs.jackson;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withPayload;

import java.io.IOException;

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

	public final DateTimeZone eRepTimeZone = DateTimeZone
			.forID("America/Los_Angeles");

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		try (CloseablePM pm = PMF.getNewPersistenceManager()) {
			Boolean enabled = new Configs(pm).getDefault().getEnabled();
			if (enabled != null && enabled) {
				LocalDate date = new LocalDate(eRepTimeZone).minus(Days.ONE);
				addToQueue(date.toString("yy-MM-dd"));
			}
		}
	}

	private void addToQueue(String date) {
		DeferredTask task = new GetNewUsersTask(date);
		Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOptions = withPayload(task).taskName(
				"GetNewUsersTask_" + date.replace('-', '_'));
		queue.addAsync(taskOptions);
	}
}