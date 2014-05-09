package com.yangjunshuai.yang.examples;

import java.util.ArrayList;
import java.util.List;

public class B3 {
	/**
	 * 懒加载的情况，一些场景下，单例对象的构建比较复杂，
	 * 需要依赖其他的资源时，而且他的资源，还没有准备完全，
	 * 不能直接初始化？
	 */
	private static B3 b = null;
	private int i = 0;
	private List list = new ArrayList();

	private B3(int i){
		this.i = i;
		new Thread(){
			public void run(){
				initList();
			}
		}.start();
	}
	
	private void initList(){
		int j=0;
		while(j<10){
			try {
				list.add(j);
				j++;
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private static class BHolder{
		static final B3 b = new B3(1000);
	}

	public static B3 getInstance(){
		
		return BHolder.b;
	}
	public int getI(){
		return i;
	}
	
	public boolean check(){
		return i>0?true:false;
	}


	public int getSize(){
		return list.size();
	}
	public void say(){
		System.out.println("ok ,i am running!");
	}
	public static void main(String[] args) throws InterruptedException {
		
		for (int i = 0; i < 10; i++) {
			Thread.sleep(100l);
		 new Thread() {
				public void run() {
					
					System.out.println(getInstance().getSize());
//					System.out.println(getInstance().check());
//					System.out.println(getInstance().hashCode());
//					System.out.println(getInstance().getI());
				}
			}.start();
			
		}
	}
}
