package com.yangjunshuai.yang.examples;

public class B {
	private static B b = new B();
	private int i = 0;
	private B(){
		this.i  = 0;
	}

	private B(int i){
		this.i = i;
	}

	public static B getInstance(){
		return b;
	}
	public int getI(){
		return i;
	}
	public void say(){
		System.out.println("ok ,i am running!");
	}
	public static void main(String[] args) {
		getInstance().say();
	}
}
