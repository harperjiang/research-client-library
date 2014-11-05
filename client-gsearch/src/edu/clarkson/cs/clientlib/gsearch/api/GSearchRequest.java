package edu.clarkson.cs.clientlib.gsearch.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import edu.clarkson.cs.httpjson.http.Request;

public class GSearchRequest extends Request<GSearchResponse> {

	public GSearchRequest() {
		super("https://ajax.googleapis.com/ajax/services/search/web?"
				+ "v=1.0&q={0}");
	}

	@Override
	protected HttpUriRequest buildRequest() {
		Validate.isTrue(!StringUtils.isEmpty(getQueryString()));
		try {
			String converted = URLEncoder.encode(getQueryString(), "utf8");
			String url = MessageFormat.format(this.getUrl(), converted);
			HttpGet get = new HttpGet(url);
			return get;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private String queryString;

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

}
