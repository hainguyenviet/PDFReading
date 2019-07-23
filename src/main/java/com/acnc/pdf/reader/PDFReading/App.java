package com.acnc.pdf.reader.PDFReading;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Tesseract tesseract = new Tesseract();
		try {
			Properties prop = loadProperties();
			tesseract.setDatapath("Tess4J\\tessdata");
			File[] files = readAllfile(prop.getProperty("path"));

			for (File file : files) {
				System.out.println("Read File:"+ file.getName());
				String text = tesseract.doOCR(file);
				writeNewFile(prop.getProperty("txtPath")+file.getName().replaceAll("pdf", "txt"), text);
				// path of your image file
				System.out.print(text);

			}
			// the path of your tess data folder
			// inside the extracted file

		} catch (TesseractException e) {
			e.printStackTrace();
		}
	}

	public static File[] readAllfile(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".pdf");
			}
		});
		return files;
	}

	private static Properties loadProperties() {
		// TODO Auto-generated method stub
		try {
			InputStream input = new FileInputStream("config.properties");
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private static void writeNewFile(String name, String content) {
		// TODO Auto-generated method stub
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(name);
			bw = new BufferedWriter(fw);
			bw.write(content);

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		} finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				System.err.format("IOException: %s%n", ex);
			}
		}

	}
}
