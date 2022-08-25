package net.b5gamer.b5wars.test.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
 
public class WatermarkTest {

	public static void main(String[] args) {
		String pdfName   = "Firefly_Role-Playing_Game_Corebook_(8423005)";
		String watermark = "Alex Packwood \\(order #8423005\\)";
		
		// now we are going to read the file and extract the content stream
		try {
			// creating a reader object that will read the file we created
			// earlier
			PdfReader reader = new PdfReader(pdfName + ".pdf");
			
			for (int page = 1; page <= reader.getNumberOfPages(); page++) {
				// we can inspect the syntax of the imported page
				byte[] streamBytes = reader.getPageContent(page);
				String contentStream = new String(streamBytes);
				System.out.println(contentStream);
				
				// we do some dirty hacking, replacing Hello World by something else
				StringBuffer buf = new StringBuffer();
				int pos = contentStream.indexOf(watermark);
				buf.append(contentStream.substring(0, pos));
				buf.append(contentStream.substring(pos + watermark.length()));
				String hackedContentStream = buf.toString();
				reader.setPageContent(page, hackedContentStream.getBytes());
			}
			
			// we create a new PDF file that contains the hacked stream
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					pdfName + "_modified.pdf"));
			stamper.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
