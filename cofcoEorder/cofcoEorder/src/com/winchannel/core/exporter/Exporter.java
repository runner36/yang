package com.winchannel.core.exporter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.winchannel.core.bean.ListColumn;

/**
 * @author xianghui
 *
 */
public abstract class Exporter {
	
	protected ListColumn[] columns;
	protected String contentType;
	
	public Exporter(ListColumn[] columns) {
		this.columns = columns;
		setContentType();
	}
	
	protected abstract void setContentType();
	
	public abstract void createTitle(String title);
	public abstract void createHeader();
	public abstract void createBody(List body, int bodyStart, int leftHeader);
	public abstract void createFooter(String footer);
	public abstract void write(OutputStream outputStream) throws IOException;
	
	
 	public void write(HttpServletResponse response, String fileName) throws IOException {
 		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1") + "." + contentType);
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
    	write(response.getOutputStream());
	}
 	
 	public void writeZip(OutputStream outputStream, String fileName) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(outputStream);
        zos.setEncoding("GBK");
    	ZipEntry ze = new ZipEntry(fileName + "." + contentType);
    	zos.putNextEntry(ze);
    	write(zos);
    	zos.close();
	}
 	
 	public void writeZip(HttpServletResponse response, String fileName) throws IOException {
 		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1") + ".zip");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
        writeZip(response.getOutputStream(), fileName);
	}
 	
}
