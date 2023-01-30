package br.com.lbenaducci.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@NoArgsConstructor(access=AccessLevel.NONE)
public class ReflectUtil {

	public static Object getValue(@NonNull String fieldName, @NonNull Object object) {
		Field field = getField(fieldName, object);
		if(field == null) {
			return null;
		}
		return getValue(field, object);
	}

	public static Object getValue(@NonNull Field field, @NonNull Object object) {
		Method getterMethod = getGetterMethod(field);
		if(getterMethod != null) {
			Object response = getMethodValue(getterMethod, object);
			if(response != null) {
				return response;
			}
		}
		return getFieldValue(field, object);
	}

	public static Field getField(@NonNull String fieldName, @NonNull Object object) {
		try {
			return object.getClass().getDeclaredField(fieldName);
		} catch(NoSuchFieldException e) {
			return null;
		}
	}

	public static Method getMethod(@NonNull Class<?> clazz, @NonNull String methodName, Class<?>... args) {
		try {
			return clazz.getDeclaredMethod(methodName, args);
		} catch(NoSuchMethodException e) {
			return null;
		}
	}

	public static Method getGetterMethod(@NonNull Field field) {
		String name = field.getName();
		Class<?> type = field.getType();
		Class<?> declaringClass = field.getDeclaringClass();
		Method method = getMethod(declaringClass, name);
		if(method != null) {
			return method;
		}
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		if(type == boolean.class || type == Boolean.class) {
			method = getMethod(declaringClass, "is" + name);
			if(method != null) {
				return method;
			}
		}
		return getMethod(declaringClass, "get" + name);
	}

	public static Object getFieldValue(@NonNull Field field, @NonNull Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch(IllegalAccessException ignored) {
		}
		return null;
	}

	public static Object getMethodValue(@NonNull Method method, @NonNull Object object, Object... args) {
		try {
			method.setAccessible(true);
			return method.invoke(object, args);
		} catch(IllegalAccessException | InvocationTargetException e) {
			return null;
		}
	}

}
