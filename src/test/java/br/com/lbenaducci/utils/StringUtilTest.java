package br.com.lbenaducci.utils;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({ "ConstantConditions" })
public class StringUtilTest {

	@Test
	void rightPadNullValue() {
		assertThrows(NullPointerException.class, () -> StringUtil.rightPad(null, 0, ' '));
	}

	@Test
	void rightPadSuccess() {
		String rightPad = StringUtil.rightPad("a", 5, ' ');
		assertEquals("a    ", rightPad);
	}

	@Test
	void rightPadBiggerLength() {
		String value = "abcdefg";
		String rightPad = StringUtil.rightPad(value, 5, ' ');
		assertEquals(value, rightPad);
	}

	@Test
	void leftPadNullValue() {
		assertThrows(NullPointerException.class, () -> StringUtil.leftPad(null, 0, ' '));
	}

	@Test
	void leftPadSuccess() {
		String leftPad = StringUtil.leftPad("a", 5, ' ');
		assertEquals("    a", leftPad);
	}

	@Test
	void leftPadBiggerLength() {
		String value = "abcdefg";
		String leftPad = StringUtil.leftPad(value, 5, ' ');
		assertEquals(value, leftPad);
	}

	public static String random(int length) {
		final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder randomStr = new StringBuilder();
		Random random = new Random();
		if(length < 0) {
			length = random.nextInt(50);
			if(length < 0) {
				length = length * -1;
			}
		}
		for(int i = 0; i < length; i++) {
			int number = random.nextInt(CHAR_LIST.length());
			char ch = CHAR_LIST.charAt(number);
			randomStr.append(ch);
		}
		return randomStr.toString();
	}

}
