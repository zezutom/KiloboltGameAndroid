package org.zezutom.android.gaming.framework.impl;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;

import org.zezutom.android.gaming.framework.Graphics;
import org.zezutom.android.gaming.framework.Image;
import org.zezutom.android.gaming.framework.ImageSource;

import java.io.IOException;
import java.io.InputStream;

public class AndroidGraphics implements Graphics {

	private AssetManager assetManager;
	
	Bitmap frameBuffer;
	
	Canvas canvas;
	
	Paint paint;
	
	Rect rectSource = new Rect();
	
	Rect rectTarget = new Rect();
	
	public AndroidGraphics(AssetManager assetManager, Bitmap frameBuffer) {
		super();
		this.assetManager = assetManager;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
	}

	@Override
	public Image newImage(String fileName, ImageFormat format) {
		final Options options = getBitMapOptions(format);
		final Bitmap bitmap = getBitmap(fileName, options);
		updateFormat(format, bitmap);
		
		return new AndroidImage(bitmap, format);
	}

	private void updateFormat(ImageFormat format, Bitmap bitmap) {
		if (bitmap == null) return;
		
		switch (bitmap.getConfig()) {
			case RGB_565:
				format = ImageFormat.RGB565;
				break;
			case ARGB_4444:
				format = ImageFormat.ARGB4444;
				break;
			default:
				format = ImageFormat.ARGB8888;
				break;
		}
		
	}

    private Bitmap getBitmap(String fileName, final Options options) {
		Bitmap bitmap = null;
        InputStream is = null;

		try {
            is = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(is, null, options);
			
			if (bitmap == null) {
				failBitmapLoad(fileName);
			}
		} catch (IOException e) {
			failBitmapLoad(fileName);
		}
		
		return bitmap;
	}

	private void failBitmapLoad(String fileName) {
		throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
	}

	private Options getBitMapOptions(ImageFormat imageFormat) {
		Config config = null;
		
		switch (imageFormat) {
			case RGB565:
				config = Config.RGB_565;
				break;
			case ARGB4444:
				config = Config.ARGB_4444;
				break;
			default:
				config = Config.ARGB_8888;
				break;
		}
		
		Options options = new Options();
		options.inPreferredConfig = config;
		
		return options;
	}

	@Override
	public void clearScreen(int color) {
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));

	}

	@Override
	public void drawLine(Point from, Point to, int color) {
		paint.setColor(color);
		canvas.drawLine(from.x, from.y, to.x, to.y, paint);
	}

	@Override
	public void drawRect(Point from, Point to, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(from.x, from.y, to.x, to.y, paint);
	}

	@Override
	public void drawImage(Image image, Point at, ImageSource imageSource) {
		setRect(rectSource, imageSource);
		setRect(rectTarget, imageSource);
		drawBitmap(image);				
	}

	private void drawBitmap(Image image) {
		canvas.drawBitmap(((AndroidImage) image).getBitmap(), 
				rectSource, rectTarget, null);
	}

	private void setRect(Rect rect, ImageSource source) {
		setRect(rect, source, 0, 0);
	}
	
	private void setRect(Rect rect, ImageSource source, int width, int height) {
		final Point position = source.getPosition();
		rect.left = position.x;
		rect.top = position.y;
		rect.right = position.x + ((width > 0) ? width : source.getWidth());
		rect.bottom = position.y + ((height > 0) ? height : source.getHeight());
	}

	@Override
	public void drawImage(Image image, Point at) {
		canvas.drawBitmap(((AndroidImage) image).getBitmap(), 
				at.x, at.y, null);
	}

	@Override
	public void drawScaledImage(Image image, Point at, ImageSource imageSource,
			int width, int height) {
		setRect(rectSource, imageSource);
		setRect(rectTarget, imageSource, width, height);
		drawBitmap(image);		
	}
	
	@Override
	public void drawString(String text, Point at, Paint paint) {
		canvas.drawText(text, at.x, at.y, paint);
	}

	@Override
	public int getWidth() {
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		return frameBuffer.getHeight();
	}

	@Override
	public void drawARGB(int red, int green, int blue, int alpha) {
		paint.setStyle(Style.FILL);
		canvas.drawARGB(alpha, red, green, blue);
	}

}
