package br.com.lbenaducci.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TxtAttribute {
	int order() default -1;

	int minLength() default 0;

	int maxLength() default Integer.MAX_VALUE;

	int length() default -1;

	char fillChar() default ' ';

	FillOrientation orientation() default FillOrientation.RIGTH;

	boolean required() default false;

	String defaultValue() default "";

	String fieldEncapsutor() default "";

	enum FillOrientation {
		RIGTH,
		LEFT
	}
}
