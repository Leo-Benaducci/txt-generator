package br.com.lbenaducci.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access=AccessLevel.NONE)
public class StringUtil {

	public static String rightPad(@NonNull String value, int length, char fillChar) {
		if(value.length() > length) {
			return value;
		}
		String padding = generatePadding(fillChar, length - value.length());
		return value + padding;
	}

	public static String leftPad(String value, int length, char fillChar) {
		if(value.length() > length) {
			return value;
		}
		String padding = generatePadding(fillChar, length - value.length());
		return padding + value;
	}

	private static String generatePadding(char fillChar, int length) {
		StringBuilder padding = new StringBuilder();
		for(int i = 0; i < length; i++) {
			padding.append(fillChar);
		}
		return padding.toString();
	}
}
