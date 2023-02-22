package br.com.lbenaducci.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TxtLine {
	boolean breakLine() default true;

	String fieldSeparator() default "";

	String startWith() default "";

	String endWith() default "";

	boolean useSuper() default false;
}
