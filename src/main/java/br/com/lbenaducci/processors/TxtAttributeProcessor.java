package br.com.lbenaducci.processors;

import br.com.lbenaducci.annotations.TxtAttribute;
import br.com.lbenaducci.utils.ReflectUtil;
import br.com.lbenaducci.utils.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.lang.reflect.Field;

@NoArgsConstructor(access=AccessLevel.NONE)
public class TxtAttributeProcessor {

	public static TxtAttribute getTxtAttribute(@NonNull Field field) {
		if(field.isAnnotationPresent(TxtAttribute.class)) {
			return field.getAnnotation(TxtAttribute.class);
		}
		return null;
	}

	public static String getValueOrDefault(@NonNull Field field, @NonNull Object object) {
		Object fieldValue = ReflectUtil.getValue(field, object);
		TxtAttribute txtAttribute = getTxtAttribute(field);
		if(txtAttribute != null) {
			verifyRequired(txtAttribute, fieldValue, field.getName());
			if(fieldValue == null && !txtAttribute.defaultValue().isEmpty()) {
				return txtAttribute.defaultValue();
			}
		}
		return fieldValue != null ? fieldValue.toString() : null;
	}

	public static String getValueFormatted(@NonNull Field field, @NonNull Object object) {
		TxtAttribute txtAttribute = getTxtAttribute(field);
		String value = getValueOrDefault(field, object);
		if(txtAttribute == null) {
			return value;
		}
		if(value == null) {
			value = "";
		}
		value = adjustMinLength(txtAttribute, value);
		value = adjustMaxLength(txtAttribute, value);
		value = txtAttribute.fieldEncapsutor() + value + txtAttribute.fieldEncapsutor();
		return value;
	}

	private static void verifyRequired(TxtAttribute txtAttribute, Object value, String fieldName) {
		if(txtAttribute.required() && value == null) {
			throw new NullPointerException("Invalid value for " + fieldName);
		}
	}

	private static String adjustMinLength(TxtAttribute txtAttribute, String value) {
		int minLength = txtAttribute.minLength();
		char fillChar = txtAttribute.fillChar();
		int length = txtAttribute.length();
		if(length > -1) {
			minLength = length;
		}
		switch(txtAttribute.orientation()) {
			case RIGTH:
				return StringUtil.rightPad(value, minLength, fillChar);
			case LEFT:
				return StringUtil.leftPad(value, minLength, fillChar);
			default:
				return value;
		}
	}

	private static String adjustMaxLength(TxtAttribute txtAttribute, String value) {
		int maxLength = txtAttribute.maxLength();
		int length = txtAttribute.length();
		if(length > -1) {
			maxLength = length;
		}
		return value.substring(0, Math.min(value.length(), maxLength));
	}

}
