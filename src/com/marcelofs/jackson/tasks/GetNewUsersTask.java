package com.marcelofs.jackson.tasks;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withPayload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.esxx.js.protocol.GAEConnectionManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class GetNewUsersTask implements DeferredTask {

	private static final long serialVersionUID = 1L;
	private final String date;

	public GetNewUsersTask(String date) {
		this.date = date;
	}

	@Override
	public void run() {
		try {
			HttpEntity response = doPost(date);
			String[] ids = parseResponse(IOUtils
					.toString(response.getContent()));
			for (String id : ids)
				if (id != null && !id.trim().isEmpty())
					addToQueue(id.trim());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private HttpEntity doPost(String date) throws ClientProtocolException,
			IOException {
		DefaultHttpClient client = new DefaultHttpClient(
				new GAEConnectionManager());
		HttpPost httpPost = new HttpPost("http://ereptools.tk/newbs/result.php");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>(4);
		{
			nvps.add(new BasicNameValuePair("country", "Brazil"));
			nvps.add(new BasicNameValuePair("from", "0"));
			nvps.add(new BasicNameValuePair("go", "SUBMIT"));
			nvps.add(new BasicNameValuePair("date", date));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

		HttpResponse uploadResponse = client.execute(httpPost);
		if (uploadResponse.getStatusLine().getStatusCode() != 200)
			throw new IOException("Error fetching new users: "
					+ uploadResponse.getStatusLine().getStatusCode());

		return uploadResponse.getEntity();
	}

	protected String[] parseResponse(String html) throws IllegalStateException,
			IOException {
		Document doc = Jsoup.parse(html);
		String idList = doc.select(".form-control").val().trim();
		return idList.split(",");
	}

	private void addToQueue(String id) {
		DeferredTask task = new MessageUserTask(id);
		Queue queue = QueueFactory.getQueue("erepublik-logged-in");
		TaskOptions taskOptions = withPayload(task).taskName(
				"MessageUserTask_" + id);
		queue.addAsync(taskOptions);
	}
}
