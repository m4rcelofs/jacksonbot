package com.marcelofs.jackson.persistence.dao;

import static java.util.Arrays.asList;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.marcelofs.jackson.persistence.data.NewUsersDay;

public class NewUsersDays extends GenericDAO<NewUsersDay> {

	public NewUsersDays(PersistenceManager pm) {
		super(NewUsersDay.class, pm);
	}

	public void add(String date, String[] ids) {
		NewUsersDay users = new NewUsersDay();
		users.setNewUsers(asList(ids));
		users.setDay(toKey(date));
		add(users);
	}

	@SuppressWarnings("unchecked")
	public List<NewUsersDay> getFrom(String date) {
		Query q = newQuery("day >= dayParam");
		q.setOrdering("day asc");
		q.declareParameters(Key.class.getName() + " dayParam");
		return (List<NewUsersDay>) q.execute(toKey(date));
	}

	public Key toKey(String date) {
		return toKey(Long.valueOf(date.replace("-", "")));
	}
}