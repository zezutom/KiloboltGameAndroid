package org.zezutom.android.gaming.framework;

import org.zezutom.android.gaming.framework.Graphics.ImageFormat;

public interface Image {
	
	int getWidth();
	
	int getHeight();
	
	ImageFormat getFormat();
	
	void dispose();
}
