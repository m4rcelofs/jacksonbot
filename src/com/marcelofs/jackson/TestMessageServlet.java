package com.marcelofs.jackson;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withPayload;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.marcelofs.jackson.tasks.MessageUserTask;

public class TestMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String id = req.getParameter("id");

		if (id == null || id.trim().isEmpty() || !id.trim().matches("\\d+")) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		addToQueue(id.trim());
	}

	private void addToQueue(String id) {
		DeferredTask task = new MessageUserTask(id);
		Queue queue = QueueFactory.getQueue("erepublik-logged-in");
		TaskOptions taskOptions = withPayload(task).taskName(
				"TestMessageTask_" + id + "_" + new Random().nextInt(100));
		queue.addAsync(taskOptions);
	}
}