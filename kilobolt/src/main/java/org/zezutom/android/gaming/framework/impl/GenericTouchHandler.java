package org.zezutom.android.gaming.framework.impl;

import java.util.ArrayList;
import java.util.List;

import org.zezutom.android.gaming.framework.Pool;
import org.zezutom.android.gaming.framework.PoolObjectFactory;
import org.zezutom.android.gaming.framework.TouchEvent;
import org.zezutom.android.gaming.framework.TouchHandler;

import android.view.MotionEvent;
import android.view.View;

public abstract class GenericTouchHandler implements TouchHandler {
	
	public static final int MAX_EVENTS = 100;
	
	private Pool<TouchEvent> touchEventPool;
	
	private List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	
	private List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	
	private float scaleX;
	
	private float scaleY;
	
	protected abstract void handleTouch(MotionEvent event);
	
	protected abstract boolean isTouched(int pointer);
	
	protected abstract int getX(int pointer);
	
	protected abstract int getY(int pointer);
	
	public GenericTouchHandler(View view, float scaleX, float scaleY) {
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
			
			@Override
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		touchEventPool = new Pool<TouchEvent>(factory, MAX_EVENTS);
		view.setOnTouchListener(this);
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			handleTouch(event);
			return true;
		}
	}
	
	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			return isTouched(pointer);
		}
	}
	
	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			return getX(pointer);
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			return getY(pointer);
		}
	}	
	
	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized (this) {
			for (TouchEvent touchEvent : touchEvents) {
				touchEventPool.free(touchEvent);
			}
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}
	
	protected TouchEvent newTouchEvent() {
		return touchEventPool.newObject();	
	}
	
	protected void addTouchEvent(TouchEvent event) {
		touchEventsBuffer.add(event);
	}
	
	protected int getScaledX(MotionEvent event) {
		return getScaledCoordinate(event.getX(), scaleX);
	}

	protected int getScaledY(MotionEvent event) {
		return getScaledCoordinate(event.getY(), scaleY);
	}
	
	private int getScaledCoordinate(float coordinate, float scale) {
		return (int) (coordinate * scale);
	}	
	
}
