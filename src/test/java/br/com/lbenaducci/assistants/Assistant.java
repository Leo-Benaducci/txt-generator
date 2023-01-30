package br.com.lbenaducci.assistants;

import br.com.lbenaducci.annotations.TxtAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@NoArgsConstructor
@FieldNameConstants
public class Assistant {
	@Getter
	@TxtAttribute(required=true)
	private String a;
	@TxtAttribute
	private Boolean b;
	private String c;
	@Getter
	@TxtAttribute(defaultValue="d")
	private String d;
	@TxtAttribute(minLength=5)
	private String e;
	@TxtAttribute(maxLength=5)
	private String f;
	@TxtAttribute(length=5)
	private String g;
	@TxtAttribute(fieldEncapsutor="\"")
	private String h;
	@TxtAttribute(orientation=TxtAttribute.FillOrientation.LEFT, minLength=5)
	private String i;
	@TxtAttribute(fillChar='0', minLength=5)
	private String j;

	public Assistant(String a){
		this.a = a;
	}

	public Assistant(String a, boolean b, String c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Assistant(String e, String f, String g, String h, String i, String j) {
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
		this.j = j;
	}

	public Boolean isB() {
		return b;
	}

	public String c() {
		return "c: " + c;
	}
}
