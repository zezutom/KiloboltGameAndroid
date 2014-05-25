package org.zezutom.android.gaming.framework;

import java.util.List;

import android.view.View.OnTouchListener;

public interface TouchHandler extends OnTouchListener {

	boolean isTouchDown(int pointer);
	
	int getTouchX(int pointer);
	
	int getTouchY(int pointer);
	
	List<TouchEvent> getTouchEvents();
}
