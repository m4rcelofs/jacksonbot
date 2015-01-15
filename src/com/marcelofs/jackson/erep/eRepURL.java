package com.marcelofs.jackson.erep;

public enum eRepURL {
	COMPOSE("http://www.erepublik.com/en/main/messages-compose/{id}"), ;

	private String url;

	private eRepURL(String url) {
		this.url = url;
	}

	public String getUrl(String id) {
		return this.url.replace("{id}", id);
	}

}
