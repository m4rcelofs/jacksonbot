package com.marcelofs.jackson;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

import com.marcelofs.jackson.persistence.CloseablePM;
import com.marcelofs.jackson.persistence.PMF;
import com.marcelofs.jackson.persistence.dao.ApiConfigs;
import com.marcelofs.jackson.persistence.data.ApiConfig;

public class APIKeyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");

		JSONObject response = new JSONObject();
		try (CloseablePM pm = PMF.getNewPersistenceManager()) {
			ApiConfig conf = new ApiConfigs(pm).getDefault();
			response.put("connector", hide(conf.getConnector()));
			response.put("userGuid", hide(conf.getUserGuid()));
			response.put("apiKey", hide(conf.getApiKey()));
		}
		resp.getWriter().write(response.toString());
	}

	private static String hide(String data) {
		if(data == null)
			return "";
		byte[] s = data.getBytes();
		for (int i = 0; i < 11; i++) {
			s = Base64.encodeBase64(s);
		}
		return new String(s);
	}
}