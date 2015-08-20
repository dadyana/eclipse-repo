package br.copy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;

public class JCopyPropertiesBranch {

	private final String pathJMX;

	JCopyPropertiesBranch(HashMap<String, String> names, String pathJMX) {
		this.pathJMX = pathJMX;
	}

public 	JCopyPropertiesBranch(String path) throws Exception {
		File file = new File(path);
		this.pathJMX = path;
		String userFolder = System.getProperty("user.home");
		String folder = "c:/temp";
		copyDirectory(file, new File(userFolder
				+ "/senior/"));
		System.out.println("Finish");

	}

    public 	JCopyPropertiesBranch(String  origin, String destiny) throws Exception {
        File file = new File(origin);
        this.pathJMX = origin;
        copyDirectory(file, new File(destiny));
        System.out.println("Finish");
        
    }

	void listFile(File properties, File senior) {
		if (properties.isDirectory()) {
			for (File f : properties.listFiles()) {

			}
		}
	}

	private static void copyfile(String srFile, String dtFile) {
		try {
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("File copied.");
		} catch (FileNotFoundException ex) {
			System.out
					.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Este aqui
	 * 
	 * @param sourceLocation
	 * @param targetLocation
	 * @throws Exception 
	 */
    public void copyDirectory(File sourceLocation, File targetLocation)
			throws Exception {
	    
	    
	    if(!sourceLocation.exists()){
           throw new  Exception("Diretório não existe "+sourceLocation.getPath());
        }

	    
	    if(!targetLocation.exists()){
	        throw new  Exception("Diretório não existe "+targetLocation.getPath());
	    }
	    

		if (sourceLocation.isDirectory()) {
			for (File f : sourceLocation.listFiles()) {

				InputStream in = new FileInputStream(f);
				OutputStream out = new FileOutputStream(new File(
						targetLocation, f.getName()));

				// Copy the bits from instream to outstream
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
	}





	public static void main(String arg[]) throws Exception {

		// String path = System.getProperty("user.dir") +
		// "\\src\\dady_plugin\\testes";
		// String path = "c:\\temp\\FormcenterServerAllTests Dev.xml";
		// String path = "c:\\temp\\AllTests.xml";
		String path = "c:\\temp\\h.txt";

		JCopyPropertiesBranch replaceFileJMX = new JCopyPropertiesBranch(path);
		// replaceFileJMX.openJMX(new
		// File("c:\\temp\\FormcenterServerAllTests Dev.xml"));

	}
}
