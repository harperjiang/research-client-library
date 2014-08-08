package edu.clarkson.cs.clientlib.gsearch.api;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import edu.clarkson.cs.clientlib.common.http.Request;

public class GSearchRequest extends Request<GSearchResponse> {

	public GSearchRequest() {
		super("https://ajax.googleapis.com/ajax/services/search/web?"
				+ "v=1.0&q={0}");
	}

	@Override
	protected HttpUriRequest buildRequest() {
		Validate.isTrue(!StringUtils.isEmpty(getQueryString()));
		String url = MessageFormat.format(this.getUrl(), getQueryString());
		HttpGet get = new HttpGet(url);
		return get;
	}

	private String queryString;

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

}
