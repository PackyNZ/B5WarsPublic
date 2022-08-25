/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.swing;

import java.io.IOException;
import java.io.Writer;

import javax.swing.JTextArea;

/**
 * An extension of {@link java.io.Writer} to enable logging to a JTextArea component 
 */
public class JTextAreaWriter extends Writer {

	private final JTextArea textArea;
	
	/**
	 * @param textArea JTextArea to write to
	 */
	public JTextAreaWriter(JTextArea textArea) {
		if (textArea == null) {
			throw new IllegalArgumentException("textArea cannot be null");
		}
		
		this.textArea = textArea;
	}
 
	/**
	 * the JTextArea to write to
	 * 
	 * @return the JTextArea to write to
	 */	
	public JTextArea getTextArea() {
		return textArea;
	}
     
	@Override
	public void close() throws IOException {
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		getTextArea().append(String.valueOf(cbuf).substring(off, off + len));
	}
	
	@Override
	public void write(String str) throws IOException {
		getTextArea().append(str);
	}
     
}
