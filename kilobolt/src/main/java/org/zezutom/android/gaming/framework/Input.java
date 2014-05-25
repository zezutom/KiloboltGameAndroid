package org.zezutom.android.gaming.framework;

import java.util.List;



public interface Input {

	boolean isTouchDown(int pointer);
	
	int getTouchTypeX(int pointer);
	
	int getTouchTypeY(int pointer);
	
	List<TouchEvent> getTouchEvents();
}
