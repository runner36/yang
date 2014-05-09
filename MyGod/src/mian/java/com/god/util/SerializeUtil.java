package com.god.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {

	public static byte[] serialize(Object object) {

		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {

			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;

		} catch (Exception e) {

		} finally{
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		return null;

	}

	public static Object unserialize(byte[] bytes) {

		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();

		} catch (Exception e) {
		} finally{
			try {
				bais.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

}