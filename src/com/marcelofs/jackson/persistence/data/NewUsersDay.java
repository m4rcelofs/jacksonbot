package com.marcelofs.jackson.persistence.data;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class NewUsersDay implements Serializable, JSONAware {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent
	// yyyyMMdd
	private Key day;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<String> newUsers;

	public Key getDay() {
		return day;
	}

	public void setDay(Key day) {
		this.day = day;
	}

	public List<String> getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(List<String> newUsers) {
		this.newUsers = newUsers;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String toJSONString() {
		JSONObject json = new JSONObject();

		StringBuffer date = new StringBuffer();
		char[] digits = String.valueOf(day.getId()).toCharArray();
		for (int i = 0; i < digits.length; i++) {
			if (i == 4 || i == 6)
				date.append("-");
			date.append(digits[i]);
		}

		json.put("day", date.toString());
		json.put("newUsers", newUsers);
		return json.toJSONString();
	}
}