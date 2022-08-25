package net.b5gamer.b5wars.test.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class SplitPDF {

	public static void main(String[] args) {
		String filename = args[0];
//		String filename = "CAT35001_TotalWarfare_Blank_RS_ALL";
		
		try {
			PdfReader reader = new PdfReader(filename + ".pdf");
	
			for (int index = 1; index <= reader.getNumberOfPages(); index++) {
				Document document = new Document(reader.getPageSizeWithRotation(1));
				PdfCopy copy = new PdfCopy(document, new FileOutputStream(new File("output" + File.separator + filename + " (Page " + index + ").pdf")));
				document.open();
				PdfImportedPage page = copy.getImportedPage(reader, index);
				copy.addPage(page);
				document.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
