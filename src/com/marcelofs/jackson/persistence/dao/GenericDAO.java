package com.marcelofs.jackson.persistence.dao;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GenericDAO<T> {

	private final Class<T> type;
	private PersistenceManager pm;

	protected GenericDAO(Class<T> type, PersistenceManager pm) {
		this.type = type;
		this.pm = pm;
	}

	protected PersistenceManager pm() {
		return pm;
	}

	protected Query newQuery(String filter) {
		try {
			Query q = pm.newQuery(type.newInstance().getClass());
			q.setFilter(filter);
			return q;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void add(T t) {
		pm.makePersistent(t);
	}

	public T get(long id) {
		return pm.getObjectById(type, id);
	}

	public Key toKey(long id) {
		return KeyFactory.createKey(type.getSimpleName(), id);
	}
}
