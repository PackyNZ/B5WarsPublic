package net.b5gamer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.batik.ext.awt.RenderingHintsKeyExt;

public class PartialSVGDocumentComponent extends SVGDocumentComponent {

	private static final long serialVersionUID = -8815223060064937422L;
	
	public PartialSVGDocumentComponent(String filename) throws IOException {
		super(filename);		
		setSize();
	}

	public PartialSVGDocumentComponent(File file) throws IOException {
		super(file);
		setSize();
	}

	public PartialSVGDocumentComponent(InputStream input) throws IOException {
		super(input);
		setSize();
	}
	
	@Override
	public void setScale(double scale) {
		super.setScale(scale);
		setSize();
	}
	
	protected void setSize() {
		Rectangle bounds = getSvgGraphics().getBounds().getBounds();
		setPreferredSize(new Dimension((int) (bounds.getWidth() * getActualScale()), (int) (bounds.getHeight() * getActualScale()) - 2));
		setSize((int) (bounds.getWidth() * getActualScale()), (int) (bounds.getHeight() * getActualScale()) - 2);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHintsKeyExt.KEY_TRANSCODING, RenderingHintsKeyExt.VALUE_TRANSCODING_PRINTING);

		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		AffineTransform transform = graphics.getTransform();
		
		// scale document to fit with iText page sizing
		graphics.transform(AffineTransform.getScaleInstance(getActualScale(), getActualScale()));

		// reposition to display document
		Rectangle2D bounds = getSvgGraphics().getBounds();
		graphics.transform(AffineTransform.getTranslateInstance(bounds.getX() * -1, bounds.getY() * -1));

		getSvgGraphics().paint(graphics);

		graphics.setTransform(transform);
	}
	
}
