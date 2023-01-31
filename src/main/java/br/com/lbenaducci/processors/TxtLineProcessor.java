package br.com.lbenaducci.processors;

import br.com.lbenaducci.annotations.TxtAttribute;
import br.com.lbenaducci.annotations.TxtLine;
import br.com.lbenaducci.utils.ReflectUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;

@NoArgsConstructor(access=AccessLevel.NONE)
public class TxtLineProcessor {

	public static TxtLine getTxtLine(@NonNull Field field) {
		if(field.isAnnotationPresent(TxtLine.class)) {
			return field.getAnnotation(TxtLine.class);
		}
		Class<?> type = field.getType();
		if(Iterable.class.isAssignableFrom(type)) {
			type = getGenericClass(type);
		}
		return getTxtLine(type);
	}

	public static TxtLine getTxtLine(@NonNull Class<?> type) {
		if(type.isAnnotationPresent(TxtLine.class)) {
			return type.getAnnotation(TxtLine.class);
		}
		return null;
	}

	public static String getLine(Object object) {
		TxtLine txtLine = getTxtLine(object.getClass());
		if(txtLine == null) {
			return "";
		}
		return generateLine(txtLine, object);
	}

	public static String getLine(Field field, Object object) {
		TxtLine txtLine = getTxtLine(field);
		Object fieldValue = ReflectUtil.getValue(field, object);
		Class<?> type = fieldValue.getClass();
		if(txtLine == null) {
			return "";
		}
		if(Iterable.class.isAssignableFrom(type)) {
			StringBuilder sb = new StringBuilder();
			for(Object o: (Iterable<?>) fieldValue) {
				sb.append(generateLine(txtLine, o));
			}
			return sb.toString();
		}
		return generateLine(txtLine, fieldValue);
	}

	private static String generateLine(@NonNull TxtLine txtLine, Object object) {
		LinkedList<Field> attributes = getFieldsSorted(object);

		StringBuilder line = new StringBuilder();
		addStartLine(txtLine, line);
		addAttributes(txtLine, attributes, line, object);
		addEndLine(txtLine, line);
		addBreakLine(txtLine, line);

		for(Field field: getLineField(object)) {
			line.append(getLine(field, object));
		}

		return line.toString();
	}

	private static LinkedList<Field> getFieldsSorted(Object object) {
		LinkedList<Field> orderFields = new LinkedList<>();
		LinkedList<Field> otherFields = new LinkedList<>();
		for(Field field: object.getClass().getDeclaredFields()) {
			TxtAttribute txtAttribute = TxtAttributeProcessor.getTxtAttribute(field);
			if(txtAttribute == null) {
				continue;
			}
			int order = txtAttribute.order();
			if(order > -1) {
				orderFields.add(order, field);
			} else {
				otherFields.add(field);
			}
		}
		orderFields.addAll(otherFields);
		return orderFields;
	}

	private static LinkedList<Field> getLineField(Object object) {
		LinkedList<Field> fields = new LinkedList<>();
		for(Field field: object.getClass().getDeclaredFields()) {
			TxtLine txtLine = getTxtLine(field);
			if(txtLine != null) {
				fields.add(field);
			}
		}
		return fields;
	}

	private static void addStartLine(TxtLine txtLine, StringBuilder sb) {
		String start = txtLine.startWith();
		if(!start.trim().isEmpty()) {
			sb.append(start).append(txtLine.fieldSeparator());
		}
	}

	private static void addAttributes(TxtLine txtLine, LinkedList<Field> attributes, StringBuilder sb, Object object) {
		for(Field attribute: attributes) {
			sb.append(TxtAttributeProcessor.getValueFormatted(attribute, object))
			  .append(txtLine.fieldSeparator());
		}
	}

	private static void addEndLine(TxtLine txtLine, StringBuilder sb) {
		String fieldSeparator = txtLine.fieldSeparator();
		String endWith = txtLine.endWith();
		if(endWith.length() > 0) {
			sb.append(endWith);
		} else {
			sb.delete(sb.length() - fieldSeparator.length(), sb.length());
		}
	}

	private static void addBreakLine(TxtLine txtLine, StringBuilder sb) {
		if(txtLine.breakLine()) {
			sb.append("\n");
		}
	}

	private static Class<?> getGenericClass(Class<?> type) {
		Type superclass = type.getGenericSuperclass();
		if(superclass instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) superclass;
			return (Class<?>) paramType.getActualTypeArguments()[0];
		}
		return type;
	}

}
