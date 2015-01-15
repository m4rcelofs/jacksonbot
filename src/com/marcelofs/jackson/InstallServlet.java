package com.marcelofs.jackson;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.data.Config;
import com.marcelofs.jackson.persistence.data.Message;

public class InstallServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try (CloseablePM pm = PMF.getNewPersistenceManager()) {

			Config config = new Config();
			{
				config.setEnabled(false);
				config.setEmail("");
				config.setPassword("");
				config.setCurrentMessageId(1l);
				config.setKey(KeyFactory.createKey(
						Config.class.getSimpleName(), 1l));
			}
			pm.makePersistent(config);

			Message message = new Message();
			{
				message.setSubject("");
				message.setMessage("");
				message.setKey(KeyFactory.createKey(
						Message.class.getSimpleName(), 1l));
			}
			pm.makePersistent(message);
		}
	}
}