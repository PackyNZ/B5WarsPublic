package net.b5gamer.b5wars.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.io.FileUtil;
import net.b5gamer.io.FilenameExtensionFilter;

public class CleanSVGDefs {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: " + CleanSVGDefs.class.getName() + " <directory>");
			System.exit(0);
		}
		
		List<File> files = FileUtil.listFiles(new File(args[0]), new FilenameExtensionFilter("svg"), true);

		if (files.size() > 0) {
			for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
				processFile(iterator.next());
			}
		} else {
			System.out.println("No svg files found!");
		}
	}

	private static void processFile(File file) {
		System.out.print("Processing " + file.getName());
	
		// read file
		String contents = readContents(file);
				
		int defsIndex = contents.lastIndexOf("</defs>");		

		if (defsIndex == -1) {
			System.out.print(" - no defs found!");			
		} else {
			// determine glyphs used
			HashMap<String, String> glyphs = new HashMap<String, String>();
			int index = defsIndex;
			while ((index = contents.indexOf("glyph", index + 1)) != -1) {
				glyphs.put(contents.substring(index + 5, contents.indexOf("\"", index)), "");
			}
			
			// remove unused glyphs
			StringBuilder newContents = new StringBuilder();
			
			index = -1;
			int appendIndex = 0;
			while ((index = contents.indexOf("glyph", index + 1)) != -1) {
				if (index > defsIndex) {
					break;
				}
				
				String glyph = contents.substring(index + 5, contents.indexOf("\"", index));
				if (glyphs.get(glyph) == null) {
					String subContents = contents.substring(appendIndex, index);
					newContents.append(subContents.substring(0, subContents.lastIndexOf("<symbol")));
					appendIndex = contents.indexOf("<", contents.indexOf("</symbol>", index) + 9);
				}
			}
			
			newContents.append(contents.substring(appendIndex, contents.length() - 1));

			// save file
			writeContents(file, newContents.toString());
			
			System.out.println(" - done!");
		}
	}
	
	private static String readContents(File file) {
		StringBuilder contents = new StringBuilder();

		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contents.toString();
	}

	private static void writeContents(File file, String contents) {
		if (!file.canWrite()) {
			throw new IllegalArgumentException("Cannot write to file " + file);
		}

		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			try {
				output.write(contents);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
