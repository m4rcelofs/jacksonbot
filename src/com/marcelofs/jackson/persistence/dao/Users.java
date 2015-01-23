package com.marcelofs.jackson.persistence.dao;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;
import com.marcelofs.jackson.persistence.data.User;

public class Users extends GenericDAO<User> {

	public final DateTimeZone eRepTimeZone = DateTimeZone
			.forID("America/Los_Angeles");

	public Users(PersistenceManager pm) {
		super(User.class, pm);
	}

	public User add(com.google.appengine.api.users.User gUser) {
		if (gUser == null)
			return null;
		User user = new User();
		user.setKey(toKey(gUser.getUserId()));
		return add(user);
	}

	@Deprecated
	public User get(long id) {
		throw new IllegalArgumentException(
				"com.google.appengine.api.users.User uses String ids");
	}

	private User get(String id) {
		return pm().getObjectById(User.class, id);
	}

	public User get(com.google.appengine.api.users.User gUser) {
		return get(gUser.getUserId());
	}

	public User getCurrent() {
		com.google.appengine.api.users.User current = UserServiceFactory
				.getUserService().getCurrentUser();
		if (current != null)
			return get(current);
		return null;
	}

	public boolean hasLicence() {
		try {
			return hasLicence(getCurrent());	
		} catch(JDOObjectNotFoundException e){
			return false;
		}
	}

	public boolean hasLicence(User user) {
		if(user == null || user.getExpires() == null)
			return false;
		LocalDate limit = new LocalDate(eRepTimeZone);
		LocalDate expiration = new LocalDate(user.getExpires(), eRepTimeZone).plusDays(1);
		return limit.isBefore(expiration);
	}

	public Key toKey(String id) {
		return KeyFactory.createKey(User.class.getSimpleName(), id);
	}
}
