package com.marcelofs.jackson.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.joda.time.LocalDate;

import com.google.appengine.api.datastore.Key;
import com.marcelofs.jackson.persistence.data.NewUsersDay;

public class NewUsersDays extends GenericDAO<NewUsersDay> {

	public NewUsersDays(PersistenceManager pm) {
		super(NewUsersDay.class, pm);
	}

	public void add(String date, String[] ids) {
		NewUsersDay users = new NewUsersDay();
		List<String> trimmedIds = new ArrayList<String>(ids.length);
		for (String id : ids)
			if (!id.trim().isEmpty())
				trimmedIds.add(id.trim());
		users.setNewUsers(trimmedIds);
		users.setDay(toKey(date));
		add(users);
	}

	@SuppressWarnings("unchecked")
	public List<NewUsersDay> getFrom(LocalDate from, LocalDate to) {
		Query q = newQuery("day >= fromParam && day <= toParam");
		q.setOrdering("day asc");
		q.declareParameters(Key.class.getName() + " fromParam, "
				+ Key.class.getName() + " toParam");
		return (List<NewUsersDay>) q.execute(toKey(from), toKey(to));
	}

	public Key toKey(String date) {
		return toKey(Long.valueOf(date.replace("-", "")));
	}

	public Key toKey(LocalDate date) {
		return toKey(format(date));
	}

	private String format(LocalDate date) {
		return date.toString("yyyyMMdd");
	}
}