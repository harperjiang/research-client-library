package edu.clarkson.cs.httpjson;

import java.lang.reflect.Field;

public class ReflectUtils {

	public static Field find(Class<?> type, String name) {
		try {
			Field f = type.getDeclaredField(name);
			return f;
		} catch (NoSuchFieldException e) {
			if (type == Object.class) {
				return null;
			}
			return find(type.getSuperclass(), name);
		}

	}
}
