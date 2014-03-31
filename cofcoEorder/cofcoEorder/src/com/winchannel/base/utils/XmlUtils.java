package com.winchannel.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtils {

	public static final Log log = LogFactory.getLog(XmlUtils.class);

	public static Document getDocument(URL url) {
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(url);
		} catch (Exception e) {
			log.info("读取XML文件是出现错误" + url, e);
		}
		return doc;
	}

	public static Document getDocument(String url) {
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(url);
		} catch (Exception e) {
			log.info("获取XML文件时出错", e);
		}
		return doc;
	}

	public static Document getDocument(File file) {
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(file);
		} catch (Exception e) {
			log.info("获取XML文件时出错", e);
		}
		return doc;
	}
	public static void writeDocument(Document doc, String fileName) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setLineSeparator("\r\n");
        format.setEncoding("GBK");
        format.setIndent("	");
        
        XMLWriter writer = null;
        try {
        	writer = new XMLWriter(new FileOutputStream(fileName), format);
        	writer.write(doc);
        }
		catch (Exception e) {
			throw new IOException("写入XML文件时出现错误" + fileName + "：" + e.getMessage());
		}
		finally {
			try {
				if (writer != null) {
					writer.close();
				}
			}
			catch(Exception e){}
		}
 	}
}