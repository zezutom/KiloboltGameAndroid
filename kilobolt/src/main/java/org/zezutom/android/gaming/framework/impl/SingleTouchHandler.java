package org.zezutom.android.gaming.framework.impl;

import org.zezutom.android.gaming.framework.TouchEvent;
import org.zezutom.android.gaming.framework.TouchType;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class SingleTouchHandler extends GenericTouchHandler {

	private boolean touched;

	private int touchX;

	private int touchY;

	public SingleTouchHandler(View view, float scaleX, float scaleY) {
		super(view, scaleX, scaleY);
	}
	
	@Override
	protected void handleTouch(MotionEvent event) {
		TouchEvent touchEvent = newTouchEvent();
		touchEvent.setType(getTouchType(event));			
		touchEvent.setPosition(getTouchPosition(event));
		addTouchEvent(touchEvent);
	}
	
	@Override
	protected boolean isTouched(int pointer) {
		return (pointer == 0) ? false : touched;
	}
	
	private TouchType getTouchType(MotionEvent event) {
		TouchType touchType = null;

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchType = TouchType.DOWN;
				break;
			case MotionEvent.ACTION_MOVE:
				touchType = TouchType.DRAGGED;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touchType = TouchType.UP;
				break;
		}

		return touchType;
	}

	private Point getTouchPosition(MotionEvent event) {
		touchX = getScaledX(event);
		touchY = getScaledY(event);

		return new Point(touchX, touchY);
	}
	
	@Override
	protected int getX(int pointer) {
		return touchX;
	}
	
	@Override
	protected int getY(int pointer) {
		return touchY;
	}
}
