package com.winchannel.core.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

public class TextExport {
	
	public static final String tempDir = "D:/temp/";
	
	private String separator;
	private File file;
	
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	public File getFile() {
		return file;
	}

	public TextExport(String separator) {
		this.separator = separator;
	}

	public void createFile(List<Object[]> body) throws IOException {
//		PrintWriter out = null;
		FileOutputStream out = null;
		file = new File(tempDir + UUID.randomUUID().toString() + ".txt");
		if (!file.getParentFile().exists()) {
			file.mkdirs();
		}
		try {
			out = new FileOutputStream(file);
//			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			Iterator iter = body.iterator();
			while (iter.hasNext()) {
				Object[] objs = (Object[]) iter.next();
				for (int i = 0; i < objs.length; i++) {
					if (i != 0) {
//						out.print(separator);
						out.write(separator.getBytes("GBK"));
					}
//					out.print(objs[i]==null?"":objs[i].toString().trim());
					out.write((objs[i]==null?"":objs[i].toString().trim()).getBytes("GBK"));
				}
//				out.println();
				out.write("\r\n".getBytes("GBK"));
			}
			out.close();
			out = null;
		}
		catch(IOException ex) {
			throw ex;
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public void write(OutputStream os) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		int readLen = 0;
		byte[] buf = new byte[1024];
		while ((readLen = is.read(buf, 0, 1024)) != -1) {
			os.write(buf, 0, readLen);
		}
		is.close();
		os.close();
	}

	public static void writeToResponse(List<Object[]> body, HttpServletResponse response, String fileName, String separator) throws IOException {
		
		TextExport exp = new TextExport(separator);
		if (body != null && body.size() > 0) {
			exp.createFile(body);
		}
		
		response.setContentType("txt");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
		exp.write(response.getOutputStream());
	}
	
}
