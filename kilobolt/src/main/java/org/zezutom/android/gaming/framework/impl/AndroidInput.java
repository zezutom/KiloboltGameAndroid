package org.zezutom.android.gaming.framework.impl;

import java.util.List;

import org.zezutom.android.gaming.framework.Input;
import org.zezutom.android.gaming.framework.TouchEvent;
import org.zezutom.android.gaming.framework.TouchHandler;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

public class AndroidInput implements Input {

	public static final int MIN_SDK = 5;	// Android 2.0
	
	private TouchHandler touchHandler;
	
	public AndroidInput(Context context, View view,
			float scaleX, float scaleY) {
		if (VERSION.SDK_INT < MIN_SDK) {
			touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		} else {
			touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	@Override
	public int getTouchTypeX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchTypeY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}
}
