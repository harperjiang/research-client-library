package edu.clarkson.cs.clientlib.common.json;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.clarkson.cs.clientlib.common.ReflectUtils;

public class BeanDeserializer<T> implements JsonDeserializer<T> {

	public BeanDeserializer() {
		super();
	}

	@SuppressWarnings("unchecked")
	public T deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		Class<?> typeClass = (Class<?>) typeOfT;
		try {
			Object instance = typeClass.getConstructor((Class[]) null)
					.newInstance((Object[]) null);
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
					JsonElement value = object.get(attrName);
					if (value != null && !value.isJsonNull()) {
						if (value.isJsonArray()
								&& desc.getPropertyType() == List.class) {
							// Get list object type and extract data from it
							@SuppressWarnings("rawtypes")
							ArrayList rawList = new ArrayList();
							Class<?> contentClass = (Class<?>) ((ParameterizedType) typeClass
									.getDeclaredField(desc.getName())
									.getGenericType()).getActualTypeArguments()[0];
							JsonArray jsonArray = (JsonArray) value;
							for (int jaidx = 0; jaidx < jsonArray.size(); jaidx++) {
								rawList.add(context.deserialize(
										jsonArray.get(jaidx), contentClass));
							}

							desc.getWriteMethod().invoke(instance, rawList);
						} else if (value.isJsonObject()) {
							Object objvalue = context.deserialize(value,
									desc.getPropertyType());
							desc.getWriteMethod().invoke(instance, objvalue);
						} else {
							if (desc.getPropertyType() == Integer.class
									|| desc.getPropertyType() == Integer.TYPE) {
								desc.getWriteMethod().invoke(instance,
										value.getAsInt());
							} else if (desc.getPropertyType() == String.class) {
								desc.getWriteMethod().invoke(instance,
										value.getAsString());
							} else if (desc.getPropertyType() == Date.class) {
								desc.getWriteMethod().invoke(instance,
										new Date(value.getAsLong() * 1000));
							} else if (desc.getPropertyType() == Double.class
									|| desc.getPropertyType() == Double.TYPE) {
								desc.getWriteMethod().invoke(instance,
										value.getAsDouble());
							} else if (desc.getPropertyType() == Boolean.class
									|| desc.getPropertyType() == Boolean.TYPE) {
								desc.getWriteMethod().invoke(instance,
										value.getAsBoolean());
							} else if (desc.getPropertyType() == BigDecimal.class) {
								desc.getWriteMethod().invoke(instance,
										value.getAsBigDecimal());
							} else {
								throw new RuntimeException("Unsupported type:"
										+ desc.getPropertyType().toString());
							}
						}
					}
				}
			}
			return (T) instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
