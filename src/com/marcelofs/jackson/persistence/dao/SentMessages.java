package com.marcelofs.jackson.persistence.dao;

import java.util.Date;

import javax.jdo.PersistenceManager;

import com.marcelofs.jackson.persistence.data.Message;
import com.marcelofs.jackson.persistence.data.SentMessage;

public class SentMessages extends GenericDAO<SentMessage> {

	public SentMessages(PersistenceManager pm) {
		super(SentMessage.class, pm);
	}

	public void markAsSent(Long userId, Message message) {
		SentMessage sent = new SentMessage();
		sent.setToUserID(toKey(Long.valueOf(userId)));
		sent.setMessage(message);
		sent.setWhen(new Date());
		add(sent);
	}
}
