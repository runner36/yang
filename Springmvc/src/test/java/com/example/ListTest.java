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
	 * arrayList ���10000000��Ԫ����ʱ��896ms
	linkedlist���10000000��Ԫ����ʱ��3260ms
	arrayList��ȡ10000��Ԫ����ʱ��0ms
	LinkedList��ȡ10000��Ԫ����ʱ��101ms
	arrayList����10000��Ԫ����ʱ��90990ms
	LinkedList����10000��Ԫ����ʱ��271ms
	arrayListɾ��10000��Ԫ����ʱ��100183ms
	LinkedListɾ��10000��Ԫ����ʱ��129ms

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
		
		System.out.println("arrayList ���"+len+"��Ԫ����ʱ��"+(d2-d1)+"ms");
		
		
		d1 = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			b.add(i);
		}
		d2 = System.currentTimeMillis();
		System.out.println("linkedlist���"+len+"��Ԫ����ʱ��"+(d2-d1)+"ms");
		
		
		int lenr = 10000;//��ȡ����
		d1 = System.currentTimeMillis();
		for (int i = 0; i < lenr; i++) {
			a.get(i);
		}
		d2 = System.currentTimeMillis();
		System.out.println("arrayList��ȡ"+lenr+"��Ԫ����ʱ��"+(d2-d1)+"ms");
		
		
		d1 = System.currentTimeMillis();
		for (int i = 0; i < lenr; i++) {
			b.get(len-i-1);
		}
		d2 = System.currentTimeMillis();
		System.out.println("LinkedList��ȡ"+lenr+"��Ԫ����ʱ��"+(d2-d1)+"ms");
		
		
		d1 = System.currentTimeMillis();
		for (int i = 0; i < lenr; i++) {
			a.indexOf(len-i-1);
		}
		d2 = System.currentTimeMillis();
		System.out.println("arrayList��λ"+lenr+"��Ԫ����ʱ��"+(d2-d1)+"ms");
		
		
		d1 = System.currentTimeMillis();
		for (int i = 0; i < lenr; i++) {
			b.indexOf(len-i-1);
		}
		d2 = System.currentTimeMillis();
		System.out.println("LinkedList��λ"+lenr+"��Ԫ����ʱ��"+(d2-d1)+"ms");
		
		
//		d1 = System.currentTimeMillis();
//		for (int i = 0; i < lenr; i++) {
//			a.add(i+lenr,i);
//		}
//		d2 = System.currentTimeMillis();
//		System.out.println("arrayList����"+lenr+"��Ԫ����ʱ��"+(d2-d1)+"ms");
//		
//		d1 = System.currentTimeMillis();
//		for (int i = 0; i < lenr; i++) {
//			b.add(i+lenr,i);
//		}
//		d2 = System.currentTimeMillis();
//		System.out.println("LinkedList����"+lenr+"��Ԫ����ʱ��"+(d2-d1)+"ms");
//		
//		d1 = System.currentTimeMillis();
//		for (int i = 0; i < lenr; i++) {
//			a.remove(i);
//		}
//		d2 = System.currentTimeMillis();
//		System.out.println("arrayListɾ��"+lenr+"��Ԫ����ʱ��"+(d2-d1)+"ms");
//		
//		d1 = System.currentTimeMillis();
//		for (int i = 0; i < lenr; i++) {
//			b.remove(i);
//		}
//		d2 = System.currentTimeMillis();
//		System.out.println("LinkedListɾ��"+lenr+"��Ԫ����ʱ��"+(d2-d1)+"ms");
	}

}