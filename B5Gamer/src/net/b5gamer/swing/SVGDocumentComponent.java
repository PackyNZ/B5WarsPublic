/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;

import org.apache.batik.ext.awt.RenderingHintsKeyExt;
import org.apache.batik.gvt.GraphicsNode;

import net.b5gamer.util.SVGUtil;

public class SVGDocumentComponent extends JComponent {

	private static final long serialVersionUID = -7326693030436120039L;
	
	private GraphicsNode svgGraphics;
	private double       scale = 1.0;
	
	public SVGDocumentComponent(final String filename) throws IOException {
		this(new File(filename));
	}

	public SVGDocumentComponent(final File file) throws IOException {
		setSvgGraphics(SVGUtil.readGraphics(file));
	}

	public SVGDocumentComponent(final InputStream input) throws IOException {
		setSvgGraphics(SVGUtil.readGraphics(input));
	}

	protected GraphicsNode getSvgGraphics() {
		return svgGraphics;
	}

	private void setSvgGraphics(GraphicsNode svgGraphics) {
		this.svgGraphics = svgGraphics;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	protected double getActualScale() {
		return SVGUtil.RESCALE * getScale();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHintsKeyExt.KEY_TRANSCODING, RenderingHintsKeyExt.VALUE_TRANSCODING_PRINTING);

		AffineTransform transform = graphics.getTransform();
		
		// scale document to 75% to fit with iText page sizing
		graphics.transform(AffineTransform.getScaleInstance(getActualScale(), getActualScale()));
			
		getSvgGraphics().paint(graphics);

		graphics.setTransform(transform);
	}

}
