package br.com.lbenaducci;

import br.com.lbenaducci.annotations.TxtLine;
import br.com.lbenaducci.processors.TxtLineProcessor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

@NoArgsConstructor(access=AccessLevel.NONE)
public class TxtGenerator {

	public static String generate(Object object) {
		StringBuilder txt = new StringBuilder();
		for(Field field: object.getClass().getDeclaredFields()) {
			TxtLine txtLine = TxtLineProcessor.getTxtLine(field);
			if(txtLine != null) {
				txt.append(TxtLineProcessor.getLine(field, object));
			}
		}
		return txt.toString();
	}

	public static void generate(Object object, File file) throws IOException {
		try(FileWriter fw = new FileWriter(file);
		    BufferedWriter bw = new BufferedWriter(fw)) {
			for(Field field: object.getClass().getDeclaredFields()) {
				TxtLine txtLine = TxtLineProcessor.getTxtLine(field);
				if(txtLine != null) {
					bw.write(TxtLineProcessor.getLine(field, object));
				}
			}
		}
	}

}
