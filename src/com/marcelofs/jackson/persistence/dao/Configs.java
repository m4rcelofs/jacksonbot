package com.marcelofs.jackson.persistence.dao;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.marcelofs.jackson.persistence.data.Config;

public class Configs extends GenericDAO<Config> {

	public Configs(PersistenceManager pm) {
		super(Config.class, pm);
	}

	public Config getDefault() {
		try {
			return get(1L);	
		} catch (JDOObjectNotFoundException e) {
			return add(new Config().setKey(toKey(1L)));
		}
	}
}