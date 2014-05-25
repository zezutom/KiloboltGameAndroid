package org.zezutom.android.gaming.framework;

import android.graphics.Paint;
import android.graphics.Point;

public interface Graphics {

	static enum ImageFormat {
		ARGB8888, ARGB4444, RGB565;
	}
	
	Image newImage(String fileName, ImageFormat imageFormat);
	
	void clearScreen(int color);
	
	void drawLine(Point from, Point to, int color);
	
	void drawRect(Point from, Point to, int color);
	
	void drawImage(Image image, Point at, ImageSource source);
	
	void drawImage(Image image, Point at);
	
	void drawScaledImage(Image image, Point at, ImageSource source, int width, int height);
	
	void drawString(String text, Point at, Paint paint);
	
	int getWidth();
	
	int getHeight();
	
	void drawARGB(int red, int green, int blue, int alpha);			
}
