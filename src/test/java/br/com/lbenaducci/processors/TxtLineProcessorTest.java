package br.com.lbenaducci.processors;

import br.com.lbenaducci.assistants.Assistant;
import br.com.lbenaducci.assistants.LineAssistant;
import br.com.lbenaducci.annotations.TxtLine;
import br.com.lbenaducci.utils.ReflectUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TxtLineProcessorTest {
	private static final LineAssistant LINE_ASSISTANT = new LineAssistant(new Assistant("a"), new Assistant("a"));

	@Test
	void getTxtLineWithoutAnnotation() {
		TxtLine txtLine = TxtLineProcessor.getTxtLine(Assistant.class);
		assertNull(txtLine);
	}

	@Test
	void getTxtLineSuccess() {
		Field field = ReflectUtil.getField(LineAssistant.Fields.a, LINE_ASSISTANT);
		TxtLine txtLine = TxtLineProcessor.getTxtLine(Objects.requireNonNull(field));
		assertNotNull(txtLine);
	}

	@Test
	void getLineWithoutAnnotation() {
		String line = TxtLineProcessor.getLine(LINE_ASSISTANT);
		assertEquals("", line);
	}

	@Test
	void getLineBreakLineTrue() {
		Field field = ReflectUtil.getField(LineAssistant.Fields.a, LINE_ASSISTANT);
		String line = TxtLineProcessor.getLine(field, LINE_ASSISTANT);
		assertTrue(line.endsWith("\n"));
	}

	@Test
	void getLineBreakLineFalse() {
		Field field = ReflectUtil.getField(LineAssistant.Fields.b, LINE_ASSISTANT);
		String line = TxtLineProcessor.getLine(field, LINE_ASSISTANT);
		assertFalse(line.endsWith("\n"));
	}

	@Test
	void getLineStart() {
		Field field = ReflectUtil.getField(LineAssistant.Fields.a, LINE_ASSISTANT);
		TxtLine txtLine = TxtLineProcessor.getTxtLine(Objects.requireNonNull(field));
		String line = TxtLineProcessor.getLine(field, LINE_ASSISTANT);
		assertTrue(line.startsWith(Objects.requireNonNull(txtLine).startWith()));
	}

	@Test
	void getLineEnd() {
		Field field = ReflectUtil.getField(LineAssistant.Fields.a, LINE_ASSISTANT);
		TxtLine txtLine = TxtLineProcessor.getTxtLine(Objects.requireNonNull(field));
		String line = TxtLineProcessor.getLine(field, LINE_ASSISTANT);
		assertTrue(line.endsWith(Objects.requireNonNull(txtLine).endWith() + "\n"));
	}

	@Test
	void getLineFileSeparator() {
		Field field = ReflectUtil.getField(LineAssistant.Fields.a, LINE_ASSISTANT);
		TxtLine txtLine = Objects.requireNonNull(TxtLineProcessor.getTxtLine(Objects.requireNonNull(field)));
		String line = TxtLineProcessor.getLine(field, LINE_ASSISTANT);
		assertTrue(line.startsWith(txtLine.startWith() + txtLine.fieldSeparator()));
		assertTrue(line.endsWith(txtLine.fieldSeparator() + txtLine.endWith() + "\n"));
	}

	@Test
	void getLineFileSeparatorWithoutStartAndEnd() {
		Field field = ReflectUtil.getField(LineAssistant.Fields.b, LINE_ASSISTANT);
		TxtLine txtLine = Objects.requireNonNull(TxtLineProcessor.getTxtLine(Objects.requireNonNull(field)));
		String line = TxtLineProcessor.getLine(field, LINE_ASSISTANT);
		assertFalse(line.startsWith(txtLine.fieldSeparator()));
		assertFalse(line.endsWith(txtLine.fieldSeparator() + "\n"));
		assertTrue(line.contains(txtLine.fieldSeparator()));
	}

}
