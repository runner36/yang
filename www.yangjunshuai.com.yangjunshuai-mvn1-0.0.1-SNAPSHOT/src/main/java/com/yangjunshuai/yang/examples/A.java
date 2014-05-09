package com.yangjunshuai.yang.examples;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.core.task.SyncTaskExecutor;

public class A {
	
	SyncTaskExecutor a = new SyncTaskExecutor();
	public void test(){
		a.execute(new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("abc");
			}
		});
	}
	CopyOnWriteArrayList<String> clist = new CopyOnWriteArrayList<String>();
	
	public void testWrite(String name,int nums){
		long d1 = System.currentTimeMillis();
		for (int i = 0; i < nums; i++) {
			clist.add(i+"");
		}
		long d2 = System.currentTimeMillis();
		System.out.println(name+"写入耗时"+(d2-d1)+"ms，当前数量"+clist.size());
	}
	
	
	public void testRead(String name,int nums){
		long d1 = System.currentTimeMillis();
		for (int i = 0; i < nums; i++) {
			clist.get(i);
		}
		long d2 = System.currentTimeMillis();
		System.out.println(name+"读取"+nums+"次，耗时"+(d2-d1)+"ms，当前数量"+clist.size());
	}
	
	
	public static void main(String[] args) {
		final A a = new A();
		
		a.testWrite("a1",10000);
		for (int i = 0; i < 100; i++) {
			final int f = i;
			new Thread(){
				public void run(){
					a.testWrite("线程"+f,1000);
					a.testRead("线程"+f, 100);
				}
			}.start();
		}
		
		System.out.println();
		
	}

}
