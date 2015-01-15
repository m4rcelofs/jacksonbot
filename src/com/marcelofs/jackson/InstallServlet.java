package com.marcelofs.jackson;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.dao.Configs;
import com.marcelofs.jackson.persistence.dao.Messages;
import com.marcelofs.jackson.persistence.data.Config;
import com.marcelofs.jackson.persistence.data.Message;

public class InstallServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try (CloseablePM pm = PMF.getNewPersistenceManager()) {
			{
				Configs dao = new Configs(pm);
				Config config = new Config();
				config.setEnabled(false);
				config.setEmail("");
				config.setPassword("");
				config.setCurrentMessageId(1L);
				config.setKey(dao.toKey(1L));
				dao.add(config);
			}
			{
				Messages dao = new Messages(pm);
				Message message = new Message();
				message.setSubject("");
				message.setMessage("");
				message.setKey(dao.toKey(1L));
				dao.add(message);
			}
		}
	}
}