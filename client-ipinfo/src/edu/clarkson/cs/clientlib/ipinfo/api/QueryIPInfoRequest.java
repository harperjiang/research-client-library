package edu.clarkson.cs.clientlib.ipinfo.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import edu.clarkson.cs.clientlib.common.http.Request;

public class QueryIPInfoRequest extends Request<QueryIPInfoResponse> {

	public QueryIPInfoRequest() {
		super(Configuration.URL);
	}

	@Override
	protected HttpUriRequest buildRequest() {
		Validate.isTrue(!StringUtils.isEmpty(getIp()));

		HttpPost request = new HttpPost(getUrl());

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(Constants.KEY, Configuration.KEY));
		nvps.add(new BasicNameValuePair(Constants.FORMAT, Constants.FORMAT_JSON));
		nvps.add(new BasicNameValuePair(Constants.IP, this.getIp()));

		request.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		return request;
	}

	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
