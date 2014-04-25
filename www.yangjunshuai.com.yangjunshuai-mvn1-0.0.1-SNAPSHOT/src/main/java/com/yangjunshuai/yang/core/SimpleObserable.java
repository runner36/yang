package com.yangjunshuai.yang.core;

import java.util.Observable;

public class SimpleObserable extends Observable {
	private int state;

	public SimpleObserable(int state) {
		super();
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
	   this.state = state;
       setChanged();
       notifyObservers(this);
	}
	
	

}
