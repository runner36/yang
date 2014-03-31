package com.winchannel.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtils {
	
	public static Object clone(Object source) {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		Object target = null;
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			out = new ObjectOutputStream(byteOut);
			out.writeObject(source);
			
			in = new ObjectInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
			target = in.readObject();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out = null;
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
		return target;
	}
	
}
