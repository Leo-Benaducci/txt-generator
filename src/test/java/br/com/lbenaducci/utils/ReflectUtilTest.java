package br.com.lbenaducci.utils;

import br.com.lbenaducci.assistants.Assistant;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({ "ConstantConditions", "ResultOfMethodCallIgnored" })
class ReflectUtilTest {
	private static final String INVALID_FIELD_NAME = "aa";
	private static final String INVALID_GET_METHOD_NAME = "getAa";
	private static final String GET_A_METHOD_NAME = "getA";
	private static final String GET_B_METHOD_NAME = "isB";
	private static final String GET_C_METHOD_NAME = "c";
	private static final String A_VALUE = "a";
	private static final boolean B_VALUE = true;
	private static final String C_VALUE = "c";
	private static final Assistant ASSISTANT = new Assistant(A_VALUE, B_VALUE, C_VALUE);

	@Test
	void getFieldNullName() {
		assertThrows(NullPointerException.class, () -> ReflectUtil.getField(null, ASSISTANT));
	}

	@Test
	void getFieldNullObject() {
		assertThrows(NullPointerException.class, () -> ReflectUtil.getField(Assistant.Fields.a, null));
	}

	@Test
	void getFieldInvalidField() {
		assertNull(ReflectUtil.getField(INVALID_FIELD_NAME, ASSISTANT));
	}

	@Test
	void getFieldSuccess() {
		Field field = ReflectUtil.getField(Assistant.Fields.a, ASSISTANT);
		assertNotNull(field);
		assertEquals(Assistant.Fields.a, field.getName());
	}

	@Test
	void getMethodNullClass() {
		assertThrows(NullPointerException.class, () -> ReflectUtil.getMethod(null, GET_A_METHOD_NAME));
	}

	@Test
	void getMethodNullMethodName() {
		assertThrows(NullPointerException.class, () -> ReflectUtil.getMethod(Assistant.class, null));
	}

	@Test
	void getMethodInvalidMethod() {
		assertNull(ReflectUtil.getMethod(Assistant.class, INVALID_GET_METHOD_NAME));
	}

	@Test
	void getMethodSuccess() {
		String methodName = GET_A_METHOD_NAME;
		Method method = ReflectUtil.getMethod(Assistant.class, methodName);
		assertNotNull(method);
		assertEquals(methodName, method.getName());
	}

	@Test
	void getGetterMethodNullField() {
		assertThrows(NullPointerException.class, () -> ReflectUtil.getGetterMethod(null));
	}

	@Test
	void getGetterMethodGetSuccess() {
		Field field = ReflectUtil.getField(Assistant.Fields.a, ASSISTANT);
		assertNotNull(field);
		Method getterMethod = ReflectUtil.getGetterMethod(field);
		assertNotNull(getterMethod);
		assertEquals(GET_A_METHOD_NAME, getterMethod.getName());
	}

	@Test
	void getGetterMethodIsSuccess() {
		Field field = ReflectUtil.getField(Assistant.Fields.b, ASSISTANT);
		assertNotNull(field);
		Method getterMethod = ReflectUtil.getGetterMethod(field);
		assertNotNull(getterMethod);
		assertEquals(GET_B_METHOD_NAME, getterMethod.getName());
	}

	@Test
	void getGetterMethodNameSuccess() {
		Field field = ReflectUtil.getField(Assistant.Fields.c, ASSISTANT);
		assertNotNull(field);
		Method getterMethod = ReflectUtil.getGetterMethod(field);
		assertNotNull(getterMethod);
		assertEquals(GET_C_METHOD_NAME, getterMethod.getName());
	}

	@Test
	void getFieldValueNullField() {
		assertThrows(NullPointerException.class, () -> ReflectUtil.getFieldValue(null, ASSISTANT));
	}

	@Test
	void getFieldValueNullObject() {
		Field field = ReflectUtil.getField(Assistant.Fields.a, ASSISTANT);
		assertNotNull(field);
		assertThrows(NullPointerException.class, () -> ReflectUtil.getFieldValue(field, null));
	}

	@Test
	void getFieldValueSuccess() {
		Field field = ReflectUtil.getField(Assistant.Fields.a, ASSISTANT);
		assertNotNull(field);
		Object fieldValue = ReflectUtil.getFieldValue(field, ASSISTANT);
		assertEquals(A_VALUE, fieldValue);
	}

	@Test
	void getMethodValueNullMethod() {
		assertThrows(NullPointerException.class, () -> ReflectUtil.getMethodValue(null, ASSISTANT));
	}

	@Test
	void getMethodValueNullObject() {
		Field field = ReflectUtil.getField(Assistant.Fields.a, ASSISTANT);
		assertNotNull(field);
		Method getterMethod = ReflectUtil.getGetterMethod(field);
		assertNotNull(getterMethod);
		assertThrows(NullPointerException.class, () -> ReflectUtil.getMethodValue(getterMethod, null));
	}

	@Test
	void getMethodValueSuccess() {
		Field field = ReflectUtil.getField(Assistant.Fields.c, ASSISTANT);
		assertNotNull(field);
		Method getterMethod = ReflectUtil.getGetterMethod(field);
		assertNotNull(getterMethod);
		Object methodValue = ReflectUtil.getMethodValue(getterMethod, ASSISTANT);
		assertEquals(ASSISTANT.c(), methodValue);
		assertNotEquals(C_VALUE, methodValue);
	}
}
