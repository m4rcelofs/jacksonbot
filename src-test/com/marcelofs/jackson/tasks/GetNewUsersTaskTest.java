package com.marcelofs.jackson.tasks;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;

public class GetNewUsersTaskTest {

	String html;

	@Before
	public void setup() throws IOException {
		File file = new File(
				"src-test/com/marcelofs/jackson/tasks/ereptools.html");
		html = FileUtils.readFileToString(file);
	}

	@Test
	public void testGetNewUsers() throws ClientProtocolException, IOException {
		GetNewUsersTask task = new GetNewUsersTask(null);
		String[] result = task.parseResponse(html);

		String[] expecteds = new String[] { "8629492", " 8629484", " 8629477",
				" 8629475", " 8629474", " 8629462", " 8629460", " 8629457",
				" 8629452", " 8629451", " 8629444", " 8629423", " 8629421",
				" 8629404", " 8629399", " 8629398", " 8629395", " 8629380",
				" 8629375", " 8629362", " 8629361", " 8629359", " 8629356",
				" 8629355", " 8629353", " 8629350", " 8629346", " 8629342",
				" 8629331", " 8629330", " 8629327", " 8629325", " 8629322",
				" 8629313", " 8629312", " 8629302", " 8629296", " 8629227",
				" 8629211", " 8629198", " 8629196", " 8629193", " 8629191",
				" 8629186", " 8629184", " 8629178", " 8629172", " 8629169",
				" 8629164", " 8629153", " 8629141", " 8629137", " 8629136",
				" 8629132", " 8629123", " 8629118", " 8629115", " 8629101",
				" 8629093", " 8629092", " 8629088", " 8629083", " 8629071",
				" 8629068", " 8629058", " 8629051", " 8629036", " 8629027",
				" 8629025", " 8629018", " 8629017", " 8628998", " 8628995",
				" 8628981", " 8628974", " 8628967", " 8628960", " 8628936" };
		assertArrayEquals(expecteds, result);
	}

}
