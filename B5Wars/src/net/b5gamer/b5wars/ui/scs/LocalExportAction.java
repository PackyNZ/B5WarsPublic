package net.b5gamer.b5wars.ui.scs;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import net.b5gamer.io.FileExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class LocalExportAction extends AbstractAction {

	private static final long serialVersionUID = -7978760944440825702L;

	private final String            format;
	private final Component         parent;
	private final ControlSheetPanel controlSheetPanel;
	private final JFileChooser      fileChooser;
	
    public LocalExportAction(final String format, final Component parent, final ControlSheetPanel controlSheetPanel) {
        super(format);
        
        if ((format == null) || !(format.trim().length() > 0)) {
            throw new IllegalArgumentException("type cannot be null or blank");
        } 
        if (parent == null) {
            throw new IllegalArgumentException("parent cannot be null");
        }
        if (controlSheetPanel == null) {
            throw new IllegalArgumentException("controlSheetPanel cannot be null");
        }
        
        this.format            = format;
        this.parent            = parent;
        this.controlSheetPanel = controlSheetPanel;
        
        this.fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
    	fileChooser.setFileFilter(new FileExtensionFilter("pdf", "Adobe PDF Files (*.pdf)"));
    }
    
    public String getFormat() {
		return format;
	}

	protected Component getParent() {
		return parent;
	}

	public ControlSheetPanel getControlSheetPanel() {
		return controlSheetPanel;
	}

	protected JFileChooser getFileChooser() {
		return fileChooser;
	}

	public void actionPerformed(ActionEvent e) {
    	getFileChooser().setSelectedFile(new File(getControlSheetPanel().getUnit().getFullName() + ".pdf"));

    	if (getFileChooser().showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
        	getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        	Document document = new Document(PageSize.LETTER, 10, 10, 10, 10);
    		try {
	    		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(getFileChooser().getSelectedFile()));
	            document.open();
	    	        
	            PdfContentByte cb = writer.getDirectContent();
	            Graphics2D graphics = cb.createGraphics(PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight()); 
	            
	            getControlSheetPanel().paintControlSheet(graphics);	            
	            
	    		graphics.dispose();
    		} catch (Exception er) {
				er.printStackTrace();
			} finally {
    			try {
    				document.close();
    			} catch (Exception er) { }
    		}
    		
    		getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
	}

}
