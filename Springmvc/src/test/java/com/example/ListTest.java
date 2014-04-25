package com.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class ListTest {
//	static List a = new ArrayList();
//
//	public void test() {
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				a.add("100");
//
//			}
//		}, 1000,2000);
//
//	}
//
//	/**
//	 * @param args
//	 * @throws InterruptedException 
//	 */
//	public static void main(String[] args) throws InterruptedException {
//		for (int i = 0; i < 100; i++) {
//			new CollectionTest().test();
//		}
//		while(true){
//			System.out.println(a.size());
//			Thread.sleep(1000l);
//		}
//	}
	
	

	/**
	 * arrayList 添加10000000个元素用时：896ms
	linkedlist添加10000000个元素用时：3260ms
	arrayList读取10000个元素用时：0ms
	LinkedList读取10000个元素用时：101ms
	arrayList插入10000个元素用时：90990ms
	LinkedList插入10000个元素用时：271ms
	arrayList删除10000个元素用时：100183ms
	LinkedList删除10000个元素用时：129ms

	 */
	
	
	public static void main(String[] args) {
		List a = new ArrayList();
		List b = new LinkedList();
		int len = 10000000;
		long d1 = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			a.add(i);
		}
		long d2 = System.currentTimeMillis();
		
		System.out.println("arrayList 添加"+len+"个元素用时："+(d2-d1)+"ms");
		
		
		d1 = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			b.add(i);
		}
		d2 = System.currentTimeMillis();
		System.out.println("linkedlist添加"+len+"个元素用时："+(d2-d1)+"ms");
		
		
		int lenr = 10000;//读取个数
		d1 = System.currentTimeMillis();
		for (int i = 0; i < lenr; i++) {
			a.get(i);
		}
		d2 = System.currentTimeMillis();
		System.out.println("arrayList读取"+lenr+"个元素用时："+(d2-d1)+"ms");
		
		
		d1 = System.currentTimeMillis();
		for (int i = 0; i < lenr; i++) {
			b.get(len-i-1);
		}
		d2 = System.currentTimeMillis();
		System.out.println("LinkedList读取"+lenr+"个元素用时："+(d2-d1)+"ms");
		
		
		d1 = System.currentTimeMillis();
		for (int i = 0; i < lenr; i++) {
			a.indexOf(len-i-1);
		}
		d2 = System.currentTimeMillis();
		System.out.println("arrayList定位"+lenr+"个元素用时："+(d2-d1)+"ms");
		
		
		d1 = System.currentTimeMillis();
		for (int i = 0; i < lenr; i++) {
			b.indexOf(len-i-1);
		}
		d2 = System.currentTimeMillis();
		System.out.println("LinkedList定位"+lenr+"个元素用时："+(d2-d1)+"ms");
		
		
//		d1 = System.currentTimeMillis();
//		for (int i = 0; i < lenr; i++) {
//			a.add(i+lenr,i);
//		}
//		d2 = System.currentTimeMillis();
//		System.out.println("arrayList插入"+lenr+"个元素用时："+(d2-d1)+"ms");
//		
//		d1 = System.currentTimeMillis();
//		for (int i = 0; i < lenr; i++) {
//			b.add(i+lenr,i);
//		}
//		d2 = System.currentTimeMillis();
//		System.out.println("LinkedList插入"+lenr+"个元素用时："+(d2-d1)+"ms");
//		
//		d1 = System.currentTimeMillis();
//		for (int i = 0; i < lenr; i++) {
//			a.remove(i);
//		}
//		d2 = System.currentTimeMillis();
//		System.out.println("arrayList删除"+lenr+"个元素用时："+(d2-d1)+"ms");
//		
//		d1 = System.currentTimeMillis();
//		for (int i = 0; i < lenr; i++) {
//			b.remove(i);
//		}
//		d2 = System.currentTimeMillis();
//		System.out.println("LinkedList删除"+lenr+"个元素用时："+(d2-d1)+"ms");
	}

}