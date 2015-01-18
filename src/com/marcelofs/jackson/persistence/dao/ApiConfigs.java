package com.marcelofs.jackson.persistence.dao;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.marcelofs.jackson.persistence.data.ApiConfig;

public class ApiConfigs extends GenericDAO<ApiConfig> {

	public ApiConfigs(PersistenceManager pm) {
		super(ApiConfig.class, pm);
	}

	public ApiConfig getDefault() {
		try {
			return get(1L);	
		} catch (JDOObjectNotFoundException e) {
			return add(new ApiConfig().setKey(toKey(1L)));
		}
	}
}