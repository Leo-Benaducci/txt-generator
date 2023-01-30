package br.com.lbenaducci.assistants;

import br.com.lbenaducci.annotations.TxtLine;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldNameConstants;

@AllArgsConstructor
@FieldNameConstants
public class LineAssistant {
	@TxtLine(startWith="start", endWith="end", fieldSeparator="|")
	private Assistant a;
	@TxtLine(fieldSeparator="|", breakLine=false)
	private Assistant b;
}
