package edu.clarkson.cs.httpjson.json;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.clarkson.cs.httpjson.ReflectUtils;

public class BeanSerializer<T> implements JsonSerializer<T> {

	private Gson gson;

	public BeanSerializer() {
	}

	public JsonElement serialize(T src, Type typeOfT,
			JsonSerializationContext context) throws JsonParseException {
		JsonObject object = new JsonObject();
		Class<?> typeClass = (Class<?>) typeOfT;
		try {
			PropertyDescriptor[] descs = PropertyUtils
					.getPropertyDescriptors(typeClass);
			for (PropertyDescriptor desc : descs) {
				if (desc.getReadMethod() != null
						&& desc.getWriteMethod() != null) {
					String attrName = null;
					JsonAttribute anno = getJsonAnnotation(ReflectUtils.find(
							typeClass, desc.getName()));
					if (null != anno) {
						attrName = anno.value();
					}

					if (null == attrName) {
						attrName = translate(desc.getName());
					}
					Object value = desc.getReadMethod().invoke(src,
							(Object[]) null);
					if (value != null) {
						if (value.getClass().isArray()
								|| value instanceof Collection) {
							object.add(attrName, gson.toJsonTree(value));
						} else if (value instanceof Integer
								|| desc.getPropertyType() == Integer.TYPE) {
							object.add(attrName, new JsonPrimitive(
									(Integer) value));
						} else if (value instanceof String) {
							object.add(attrName, new JsonPrimitive(
									(String) value));
						} else if (value instanceof Date) {
							long val = ((Date) value).getTime() / 1000;
							object.add(attrName, new JsonPrimitive(val));
						} else if (value instanceof Double
								|| desc.getPropertyType() == Double.TYPE) {
							object.add(attrName, new JsonPrimitive(
									(Double) value));
						} else if (value instanceof Boolean
								|| desc.getPropertyType() == Boolean.TYPE) {
							object.add(attrName, new JsonPrimitive(
									(Boolean) value));
						} else {
							object.add(attrName, context.serialize(value));
						}
					}
				}
			}
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	static Pattern UPPERCASE = Pattern.compile("^.*([A-Z]).*$");

	public static final String translate(String input) {
		String result = input;
		while (true) {
			Matcher matcher = UPPERCASE.matcher(result);
			if (!matcher.matches()) {
				break;
			}
			String upper = matcher.group(1);
			String lower = "_" + (char) (upper.charAt(0) + ('a' - 'A'));
			result = result.replaceAll(upper, lower);
		}
		return result;
	}

	protected JsonAttribute getJsonAnnotation(Field field) {
		for (Annotation anno : field.getDeclaredAnnotations()) {
			if (anno.annotationType() == JsonAttribute.class) {
				return (JsonAttribute) anno;
			}
		}
		return null;
	}
}
