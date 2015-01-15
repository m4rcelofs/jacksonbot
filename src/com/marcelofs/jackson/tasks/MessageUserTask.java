package com.marcelofs.jackson.tasks;

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.marcelofs.jackson.erep.ErepLoginException;
import com.marcelofs.jackson.erep.ErepScrapper;
import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.dao.Configs;
import com.marcelofs.jackson.persistence.dao.Messages;
import com.marcelofs.jackson.persistence.dao.SentMessages;
import com.marcelofs.jackson.persistence.data.Config;
import com.marcelofs.jackson.persistence.data.Message;

public class MessageUserTask implements DeferredTask {

	private static final long serialVersionUID = 1L;
	private final String id;
	private final boolean checkSent;

	public MessageUserTask(String id) {
		this(id, true);
	}

	public MessageUserTask(String id, boolean checkSent) {
		this.id = id;
		this.checkSent = checkSent;
	}

	@Override
	public void run() {

		try (CloseablePM pm = PMF.getNewPersistenceManager()) {

			SentMessages sent = new SentMessages(pm);

			if (checkSent) {
				try {
					sent.get(Long.valueOf(id));
					return;
				} catch (JDOObjectNotFoundException e) {
					// not sent, continue
				}
			}

			Message m = sendMessage(pm);
			sent.markAsSent(Long.valueOf(id), m);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Message sendMessage(PersistenceManager pm) throws IOException,
			ErepLoginException {
		Config config = new Configs(pm).getDefault();
		Message message = new Messages(pm).get(config.getCurrentMessageId());

		ErepScrapper scrapper = new ErepScrapper(config.getEmail(),
				config.getPassword());
		scrapper.login();
		scrapper.sendMessage(id, message.getSubject(), message.getMessage());
		scrapper.close();

		return message;
	}
}