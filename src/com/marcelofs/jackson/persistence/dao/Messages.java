package com.marcelofs.jackson.persistence.dao;

import javax.jdo.PersistenceManager;

import com.marcelofs.jackson.persistence.data.Message;

public class Messages {

	private PersistenceManager pm;

	public Messages(PersistenceManager pm) {
		this.pm = pm;
	}

	public Message get(Long id) {
		return pm.getObjectById(Message.class, id);
	}
}