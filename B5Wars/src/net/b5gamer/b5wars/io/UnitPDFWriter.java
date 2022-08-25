package net.b5gamer.b5wars.io;

import java.awt.Graphics2D;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import net.b5gamer.b5wars.ui.scs.ControlSheetPanel;
import net.b5gamer.b5wars.ui.scs.ControlSheetPanelFactory;
import net.b5gamer.b5wars.ui.scs.UsageMode;
import net.b5gamer.b5wars.unit.Unit;

public final class UnitPDFWriter {

	private UnitPDFWriter() {
	}
	
	public static final void write(Unit unit, OutputStream output) throws Exception {
		write(ControlSheetPanelFactory.createControlSheetPanel(unit, UsageMode.VIEW), output);
	}
	
	public static final void write(ControlSheetPanel controlSheetPanel, OutputStream output) throws Exception {
		Document document = new Document(PageSize.LETTER, 10, 10, 10, 10);

		try {
    		PdfWriter writer = PdfWriter.getInstance(document, output);
            document.open();
    	        
            PdfContentByte cb = writer.getDirectContent();
            Graphics2D graphics = cb.createGraphics(PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight()); 
            
            controlSheetPanel.paintControlSheet(graphics);	            
            
    		graphics.dispose();
		} finally {
			try {
				document.close();
			} catch (Exception er) { }
		}		
	}
	
}
