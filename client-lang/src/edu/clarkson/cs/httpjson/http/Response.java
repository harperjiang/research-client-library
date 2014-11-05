package edu.clarkson.cs.httpjson.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class Response<T extends Object> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected ResponseError error;

	private JsonParser parser;

	private Gson gson;

	protected Response() {
		super();
	}

	protected void process(HttpResponse response) throws IOException {
		this.statusCode = response.getStatusLine().getStatusCode();

		if (statusCode >= HttpStatus.SC_OK
				&& statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
			result = buildResult(response.getEntity().getContent());
		} else {
			logger.warn(MessageFormat.format("{0} Http Exception", statusCode));
			result = null;
			try {
				error = buildError(statusCode, response.getEntity()
						.getContent());
			} catch (Exception e) {
				error = new ResponseError();
				error.setCode(statusCode);
			}
		}
	}

	public JsonParser getParser() {
		return parser;
	}

	public void setParser(JsonParser parser) {
		this.parser = parser;
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	protected abstract ResponseError buildError(int errorCode,
			InputStream content);

	protected T buildResult(InputStream content) {
		JsonElement json = getParser().parse(new InputStreamReader(content));
		return buildResult(json);
	}

	public static boolean isNull(JsonElement element) {
		return element == null || element.isJsonNull();
	}

	protected abstract T buildResult(JsonElement json);

	protected int statusCode;

	protected T result;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public T getResult() {
		return result;
	}

	public ResponseError getError() {
		return error;
	}

	public void setError(ResponseError error) {
		this.error = error;
	}

}
