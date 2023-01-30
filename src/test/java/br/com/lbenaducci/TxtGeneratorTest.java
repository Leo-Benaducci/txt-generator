package br.com.lbenaducci;

import br.com.lbenaducci.assistants.Assistant;
import br.com.lbenaducci.assistants.LineAssistant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TxtGeneratorTest {
	private static final LineAssistant LINE_ASSISTANT = new LineAssistant(new Assistant("a"), new Assistant("a"));

	@Test
	void generatorLineCount() {
		String generate = TxtGenerator.generate(LINE_ASSISTANT);
		String[] split = generate.split("\n");
		assertEquals(2, split.length);
	}
}
