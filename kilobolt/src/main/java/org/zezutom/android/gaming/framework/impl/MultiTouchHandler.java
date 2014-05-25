package org.zezutom.android.gaming.framework.impl;

import org.zezutom.android.gaming.framework.TouchEvent;
import org.zezutom.android.gaming.framework.TouchType;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class MultiTouchHandler extends GenericTouchHandler {

	public static final int MAX_TOUCHPOINTS = 10;
	
	private boolean[] touched = new boolean[MAX_TOUCHPOINTS];
	
	private int[] touchX = new int[MAX_TOUCHPOINTS];
	
	private int[] touchY = new int[MAX_TOUCHPOINTS];
	
	private int[] id = new int[MAX_TOUCHPOINTS];
		
	public MultiTouchHandler(View view, float scaleX, float scaleY) {
		super(view, scaleX, scaleY);
	}

	@Override
	protected void handleTouch(MotionEvent event) {
		final int eventAction = event.getAction();
		int action = eventAction & MotionEvent.ACTION_MASK;
		int pointerIndex = (eventAction & MotionEvent.ACTION_POINTER_INDEX_MASK) 
				>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		int pointerCount = event.getPointerCount();			
		TouchEvent touchEvent = null;
		
		for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
			if (i >= pointerCount) {
				touched[i] = false;
				id[i] = -1;
				continue;
			}
			
			if (eventAction != MotionEvent.ACTION_MOVE && i != pointerIndex) {					
				continue;
			}

			int pointerId = event.getPointerId(i);
			
			switch (action) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
				case MotionEvent.ACTION_MOVE:
					final TouchType type = (action == MotionEvent.ACTION_MOVE) ? TouchType.DRAGGED : TouchType.DOWN;
					touchEvent = createTouchEvent(event, type, pointerId);
					setTouched(i, pointerId);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					touchEvent = createTouchEvent(event, TouchType.UP, pointerId);
					setTouched(i, -1);
					break;					
			}
			addTouchEvent(touchEvent);				
		}
	}
		
	private void setTouched(int index, int pointerId) {
		touched[index] = pointerId > 0;
		id[index] = pointerId;
	}
	
	private TouchEvent createTouchEvent(MotionEvent event, TouchType type, int pointer) {
		TouchEvent touchEvent = newTouchEvent();
		touchEvent.setType(type);
		touchEvent.setPointer(pointer);
		touchEvent.setPosition(getTouchPosition(event, pointer));
		
		return touchEvent;
	}
	
	private Point getTouchPosition(MotionEvent event, int index) {
		touchX[index] = getScaledX(event);
		touchY[index] = getScaledY(event);
		return new Point(touchX[index], touchY[index]);
	}
	
	@Override
	protected boolean isTouched(int pointer) {
		int index = getIndex(pointer);		
		return (index < 0 || index >= touched.length) ? false : touched[index];
 	}
			
	private int getIndex(int pointer) {
		int index = -1;
		for (int i = 0; i < id.length; i++) {
			if (id[i] == pointer) {
				index = i;
				break;
			}
		}
		return index;
	}

	@Override
	protected int getX(int pointer) {
		return getCoordinate(pointer, touchX);
	}
	
	@Override
	protected int getY(int pointer) {
		return getCoordinate(pointer, touchY);
	}
	
	private int getCoordinate(int pointer, int[] coordinates) {
		final int index = getIndex(pointer);
		return (index < 0 || index >= coordinates.length) ? 0 : coordinates[index];	
	}

}
