/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.util;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

public final class SVGUtil {

	public static final double RESCALE = 0.75;
	public static final AffineTransform RESCALE_TRANSFORM = AffineTransform.getScaleInstance(RESCALE, RESCALE);

	private SVGUtil() {
	}
	
	public static final SVGDocument readSVGDocument(final File file) throws IOException {
		return readSVGDocument(new BufferedInputStream(new FileInputStream(file)));
	}
	
	public static final SVGDocument readSVGDocument(final InputStream input) throws IOException {
		String                parser  = XMLResourceDescriptor.getXMLParserClassName();
	    SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

	    return factory.createSVGDocument(null, input);
	}

	public static final GraphicsNode readGraphics(final File file) throws IOException {
		return readGraphics(readSVGDocument(file));
	}

	public static final GraphicsNode readGraphics(final InputStream input) throws IOException {
		return readGraphics(readSVGDocument(input));
	}

	public static final GraphicsNode readGraphics(final SVGDocument document) {
		UserAgent      userAgent = new UserAgentAdapter();
		DocumentLoader loader    = new DocumentLoader(userAgent);
		BridgeContext  context   = new BridgeContext(userAgent, loader);
		context.setDynamicState(BridgeContext.DYNAMIC);
		GVTBuilder     builder   = new GVTBuilder();
		
		return builder.build(context, document);		
	}
	
	public static final Shape readShape(final File file) throws IOException {
		return readShape(readSVGDocument(file));
	}

	public static final Shape readShape(final InputStream input) throws IOException {
		return readShape(readSVGDocument(input));
	}

	public static final Shape readShape(final SVGDocument document) throws IOException {	    
		return readGraphics(document).getOutline();
	}
	
}
