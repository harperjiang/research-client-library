package edu.clarkson.cs.common;

import java.util.HashMap;
import java.util.Map;

public class BeanContext {

	private Map<String, Object> context;

	private BeanContext() {
		this.context = new HashMap<String, Object>();
	}

	private static BeanContext instance = new BeanContext();

	public static BeanContext get() {
		return instance;
	}

	public void put(String name, Object bean) {
		context.put(name, bean);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) context.get(name);
	}
}
