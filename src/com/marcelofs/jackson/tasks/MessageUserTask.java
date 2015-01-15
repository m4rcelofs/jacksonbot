package com.marcelofs.jackson.tasks;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.marcelofs.jackson.erep.ErepScrapper;
import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.dao.Configs;
import com.marcelofs.jackson.persistence.dao.Messages;
import com.marcelofs.jackson.persistence.data.Config;
import com.marcelofs.jackson.persistence.data.Message;

public class MessageUserTask implements DeferredTask {

	private static final long serialVersionUID = 1L;
	private final String id;

	public MessageUserTask(String id) {
		this.id = id;
	}

	@Override
	public void run() {

		try (CloseablePM pm = PMF.getNewPersistenceManager()) {

			Config config = new Configs(pm).getDefault();
			Message message = new Messages(pm)
					.get(config.getCurrentMessageId());

			ErepScrapper scrapper = new ErepScrapper(config.getEmail(),
					config.getPassword());
			scrapper.login();
			scrapper.sendMessage(id, message.getSubject(), message.getMessage());
			scrapper.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}