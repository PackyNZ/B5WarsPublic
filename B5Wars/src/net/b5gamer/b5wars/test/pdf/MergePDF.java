package net.b5gamer.b5wars.test.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.io.FileUtil;
import net.b5gamer.io.FilenameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class MergePDF {

	public static void main(String[] args) {
//		String directory = args[0];
		String directory = "C:\\Gaming\\Babylon 5 Wars\\SCSs\\Official\\Pak'ma'ra";	
		Document document = null;
		PdfCopy copy = null;
		
		try {
			List<File> pdfFiles = FileUtil.listFiles(new File(directory), new FilenameExtensionFilter("pdf"), false);

			for (Iterator<File> iterator = pdfFiles.iterator(); iterator.hasNext();) {
				PdfReader reader = new PdfReader(new FileInputStream(iterator.next()));
				
				for (int index = 1; index <= reader.getNumberOfPages(); index++) {
					if (document == null) {
						document = new Document(reader.getPageSizeWithRotation(1));
//						copy = new PdfCopy(document, new FileOutputStream(new File("output" + File.separator + directory + ".pdf")));
						copy = new PdfCopy(document, new FileOutputStream(new File(directory + ".pdf")));
						document.open();
					}
					
					PdfImportedPage page = copy.getImportedPage(reader, index);
					copy.addPage(page);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			try {
				if (document != null) {
					document.close();
				}
			} catch (Exception er) { }
		}		
	}

}
