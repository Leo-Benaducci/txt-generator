package br.com.lbenaducci.processors;

import br.com.lbenaducci.assistants.Assistant;
import br.com.lbenaducci.annotations.TxtAttribute;
import br.com.lbenaducci.utils.ReflectUtil;
import br.com.lbenaducci.utils.StringUtilTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TxtAttributeProcessorTest {
	private static final String SHORT_VALUE = StringUtilTest.random(3);
	private static final String LONG_VALUE = StringUtilTest.random(10);
	private static final String RANDOM_VALUE = StringUtilTest.random(-1);
	private static final Assistant ASSISTANT = new Assistant(SHORT_VALUE, LONG_VALUE, RANDOM_VALUE, SHORT_VALUE,
	                                                         SHORT_VALUE, SHORT_VALUE);

	@Test
	void getTxtAttributeWithoutAnnotation() {
		Field field = ReflectUtil.getField(Assistant.Fields.c, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		assertNull(txtAttribute);
	}

	@Test
	void getTxtAttributeSuccess() {
		Field field = ReflectUtil.getField(Assistant.Fields.b, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		assertNotNull(txtAttribute);
	}

	@Test
	void getValueOrDefaultRequiredFieldNull() {
		Field field = ReflectUtil.getField(Assistant.Fields.a, ASSISTANT);
		assertThrows(NullPointerException.class, () -> TxtAttributeProcessor.getValueOrDefault(field, ASSISTANT));
	}

	@Test
	void getValueOrDefaultFieldNullNonDefaultValue() {
		Field field = ReflectUtil.getField(Assistant.Fields.b, ASSISTANT);
		String value = TxtAttributeProcessor.getValueOrDefault(field, ASSISTANT);
		assertNull(value);
	}

	@Test
	void getValueOrDefaultFieldNull() {
		Field field = ReflectUtil.getField(Assistant.Fields.d, ASSISTANT);
		String value = TxtAttributeProcessor.getValueOrDefault(field, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		assertNull(ASSISTANT.getD());
		assertEquals(Objects.requireNonNull(txtAttribute).defaultValue(), value);
	}

	@Test
	void getValueOrDefaultFieldWithValue() {
		Field field = ReflectUtil.getField(Assistant.Fields.c, ASSISTANT);
		String value = TxtAttributeProcessor.getValueOrDefault(field, ASSISTANT);
		assertEquals(ASSISTANT.c(), value);
	}

	@Test
	void getValueFormattedMinLength() {
		Field field = ReflectUtil.getField(Assistant.Fields.e, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		String formatted = TxtAttributeProcessor.getValueFormatted(field, ASSISTANT);
		assertEquals(Objects.requireNonNull(txtAttribute).minLength(), formatted.length());
	}

	@Test
	void getValueFormattedMaxLength() {
		Field field = ReflectUtil.getField(Assistant.Fields.f, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		String formatted = TxtAttributeProcessor.getValueFormatted(field, ASSISTANT);
		assertEquals(Objects.requireNonNull(txtAttribute).maxLength(), formatted.length());
	}

	@Test
	void getValueFormattedLength() {
		Field field = ReflectUtil.getField(Assistant.Fields.g, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		String formatted = TxtAttributeProcessor.getValueFormatted(field, ASSISTANT);
		assertEquals(Objects.requireNonNull(txtAttribute).length(), formatted.length());
	}

	@Test
	void getValueFormattedFieldEncapsulator() {
		Field field = ReflectUtil.getField(Assistant.Fields.h, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		String formatted = TxtAttributeProcessor.getValueFormatted(field, ASSISTANT);
		String fieldEncapsutor = Objects.requireNonNull(txtAttribute).fieldEncapsutor();
		assertTrue(formatted.startsWith(fieldEncapsutor) && formatted.endsWith(fieldEncapsutor));
	}

	@Test
	void getValueFormatedOrientation() {
		Field field = ReflectUtil.getField(Assistant.Fields.i, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		String formatted = TxtAttributeProcessor.getValueFormatted(field, ASSISTANT);
		String fillChar = String.valueOf(Objects.requireNonNull(txtAttribute).fillChar());
		assertTrue(formatted.startsWith(fillChar));
	}

	@Test
	void getValueFormattedFillChar() {
		Field field = ReflectUtil.getField(Assistant.Fields.j, ASSISTANT);
		TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(Objects.requireNonNull(field));
		String formatted = TxtAttributeProcessor.getValueFormatted(field, ASSISTANT);
		String fillChar = String.valueOf(Objects.requireNonNull(txtAttribute).fillChar());
		assertTrue(formatted.endsWith(fillChar));
	}
}
