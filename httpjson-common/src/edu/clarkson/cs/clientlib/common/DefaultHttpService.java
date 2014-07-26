package edu.clarkson.cs.clientlib.common;

import edu.clarkson.cs.clientlib.common.http.Request;
import edu.clarkson.cs.clientlib.common.http.Response;

public class DefaultHttpService {
	
	protected <T extends Response<?>> T execute(Request<T> request)
			throws Exception {
		while (true) {
			try {
				T response = request.execute();
				if (null == response.getError()) {
					return response;
				} else {
					System.out.println(response.getError().getMessage());
				}
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(2000);
			}
		}
	}
}
