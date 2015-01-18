package com.marcelofs.jackson.persistence.data;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ApiConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent
	private Key key;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String apiKey;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String userGuid;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String connector;

	public Key getKey() {
		return key;
	}

	public ApiConfig setKey(Key key) {
		this.key = key;
		return this;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

}
