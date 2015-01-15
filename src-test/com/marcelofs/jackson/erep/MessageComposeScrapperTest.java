package com.marcelofs.jackson.erep;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

public class MessageComposeScrapperTest {

	String html;

	@Before
	public void setup() throws IOException {
		File file = new File("src-test/com/marcelofs/jackson/erep/2808593.html");
		html = FileUtils.readFileToString(file);
	}

	@Test
	public void test() {
		Document writePage = Jsoup.parse(html);
		String _token = writePage.select("#_token").attr("value").trim();

		String startNick = "prePopulate: [{\"id\":\"" + "4"
				+ "\",\"name\":\"";
		String endNick = "composeDisabled: true,";
		
		String citizen_name = html.substring(html.indexOf(startNick) + startNick.length(),
				html.indexOf(endNick));
		citizen_name = citizen_name.replace("\"}],", "").trim();

		assertEquals("1234567890", _token);
		assertEquals("nick", citizen_name);

	}

}
