package org.zezutom.android.gaming.framework;

import android.graphics.Point;

public class TouchEvent {
	
	private TouchType type;
	
	private Point position;
	
	private int pointer;

	public TouchType getType() {
		return type;
	}

	public void setType(TouchType type) {
		this.type = type;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}		
}