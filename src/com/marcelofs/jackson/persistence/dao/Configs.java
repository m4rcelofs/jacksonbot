package com.marcelofs.jackson.persistence.dao;

import javax.jdo.PersistenceManager;

import com.marcelofs.jackson.persistence.data.Config;

public class Configs {

	private PersistenceManager pm;

	public Configs(PersistenceManager pm) {
		this.pm = pm;
	}

	public Config getDefault() {
		return pm.getObjectById(Config.class, 1l);
	}
}