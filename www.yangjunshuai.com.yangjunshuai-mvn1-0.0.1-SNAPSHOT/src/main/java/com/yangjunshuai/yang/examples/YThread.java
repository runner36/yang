package com.yangjunshuai.yang.examples;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PrintStream;

import org.springframework.util.StringUtils;

class YThread extends Thread{

	PipedInputStream inputStream;
	PrintStream printStream;
	public YThread(PipedInputStream inputStream, PrintStream printStream) {
		super();
		this.inputStream = inputStream;
		this.printStream = printStream;
	}
	
	
	@Override
	public void run() {
		super.run();
		
		DataInputStream in  = new DataInputStream(inputStream);
		//byte[] buf = new byte[1024]
		String con;
		try {
			con = in.readLine();
			while(con!=null && con.length()>0){
				printStream.write(con.getBytes());
				con = in.readLine();	
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	
	
}