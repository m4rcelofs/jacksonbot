package com.marcelofs.jackson.persistence.dao;

import javax.jdo.PersistenceManager;

import com.marcelofs.jackson.persistence.data.Message;

public class Messages extends GenericDAO<Message> {

	public Messages(PersistenceManager pm) {
		super(Message.class, pm);
	}
}