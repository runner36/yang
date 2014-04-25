package com.yangjunshuai.yang.core;

import java.util.Observable;

public class SimpleListener implements MyObserver{

	public void update(Observable o, Object arg) {
		System.out.println(((SimpleObserable)o).getState());
		System.out.println(((SimpleObserable)arg).getState());
		doAction();
	}

	public void doAction() {
		System.out.println("i will do it!");
	}

}
