package com.yangjunshuai.yang.examples;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

/**
 * java管道,其实就是多线程的生产者与消费者模式
 * @author yangjunshuai
 *
 */
class PipedMain{
	
	
	public static void main(String[] args) {
	
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in = new PipedInputStream();
		PrintStream print = new PrintStream(System.out);
		try {
			in.connect(out);
			new YThread(in, print).start();
			Thread.sleep(2000l);
			out.write("hello ,how are you \n\r".getBytes());
			Thread.sleep(2000l);
			out.write("hello ,how are you again \n\r".getBytes());
			Thread.sleep(2000l);
			out.write("hello ,how are you again \n\r".getBytes());
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}