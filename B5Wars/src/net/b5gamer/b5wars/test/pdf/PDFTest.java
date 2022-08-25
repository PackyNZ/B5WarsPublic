package net.b5gamer.b5wars.test.pdf;

import java.awt.Image;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PDFTest {

	public static void main(String[] args) {
		try {
	        PdfReader reader = new PdfReader("narn g'quan heavy cruiser.pdf");      
	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("modified g'quan.pdf"));
	        	        
			PdfContentByte cb = stamper.getOverContent(1);
			Image weapon = ImageIO.read(PDFTest.class.getResourceAsStream("Heavy Laser Cannon.png"));
					
			cb.saveState();
			com.itextpdf.text.Image weaponImage = com.itextpdf.text.Image.getInstance(weapon, null);
			weaponImage.setAbsolutePosition(300, 300);
			cb.addImage(weaponImage);
			cb.restoreState();
			
	        stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
