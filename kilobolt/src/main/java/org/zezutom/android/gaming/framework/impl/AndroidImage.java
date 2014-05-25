package org.zezutom.android.gaming.framework.impl;

import org.zezutom.android.gaming.framework.Graphics.ImageFormat;
import org.zezutom.android.gaming.framework.Image;

import android.graphics.Bitmap;

public class AndroidImage implements Image {

	private Bitmap bitmap;
	
	private ImageFormat format;
	
	public AndroidImage(Bitmap bitMap, ImageFormat format) {
		this.bitmap = bitMap;
		this.format = format;
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public ImageFormat getFormat() {
		return format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
}
