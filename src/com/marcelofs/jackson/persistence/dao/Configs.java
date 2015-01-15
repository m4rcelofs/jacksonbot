package com.marcelofs.jackson.persistence.dao;

import javax.jdo.PersistenceManager;

import com.marcelofs.jackson.persistence.data.Config;

public class Configs extends GenericDAO<Config> {

	public Configs(PersistenceManager pm) {
		super(Config.class, pm);
	}

	public Config getDefault() {
		return get(1L);
	}
}