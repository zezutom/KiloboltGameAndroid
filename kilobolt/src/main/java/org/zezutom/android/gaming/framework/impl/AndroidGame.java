package org.zezutom.android.gaming.framework.impl;

import org.zezutom.android.gaming.framework.Audio;
import org.zezutom.android.gaming.framework.FileIO;
import org.zezutom.android.gaming.framework.Game;
import org.zezutom.android.gaming.framework.Graphics;
import org.zezutom.android.gaming.framework.Input;
import org.zezutom.android.gaming.framework.Screen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public abstract class AndroidGame extends Activity implements Game {

	public static final int PX_X = 800;
	
	public static final int PX_Y = 1280;
	
	private AndroidFastRenderView renderView;
	
	private Graphics graphics;
	
	private Audio audio;
	
	private Input input;
	
	private FileIO fileIO;
	
	private Screen screen;
	
	private WakeLock wakeLock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		final Bitmap frameBuffer = getFrameBuffer();
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = createInput(frameBuffer);
		screen = getInitScreen();
		setContentView(renderView);
		
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "KinoboltGame");
		
	}
	
	private Input createInput(Bitmap frameBuffer) {		
		final float scaleX = frameBuffer.getWidth() / getDisplayWidth();
		final float scaleY = frameBuffer.getHeight() / getDisplayHeight();
		
		return new AndroidInput(this, renderView, scaleX, scaleY);
	}

	private int getDisplayWidth() {
		return getSize(true);
	}

	private int getDisplayHeight() {
		return getSize(false);
	}
		
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private int getSize(boolean isWidth) {		
		final Display display = getWindowManager().getDefaultDisplay();
		int size = 0;
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			// at least version 13: use getSize()
			Point outSize = new Point();
			display.getSize(outSize);
			size = (isWidth) ? outSize.x : outSize.y; 
		}
		else {
			// older Android: use getWidth(), getHeight()
			size = (isWidth) ? display.getWidth() : display.getHeight();
		}
		
		return size;
	}
	
	private boolean isPortrait() {
		return getResources()
				.getConfiguration()
				.orientation == Configuration.ORIENTATION_PORTRAIT;
	}
	
	private Bitmap getFrameBuffer() {
		final boolean portrait = isPortrait();
		final int width = (portrait) ? PX_X : PX_Y;
		final int height = (portrait) ? PX_Y : PX_X;

		return Bitmap.createBitmap(width, height, Config.RGB_565);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();
		
		if (isFinishing()) {
			screen.dispose();
		}
	}
	
	@Override
	public Input getInput() {
		return input;
	}
	
	@Override
	public FileIO getFileIO() {
		return fileIO;
	}
	
	@Override
	public Graphics getGraphics() {
		return graphics;
	}
	
	@Override
	public Audio getAudio() {
		return audio;
	}
	
	@Override
	public void setScreen(Screen screen) {
		if (screen == null) {
			throw new IllegalArgumentException("Screen must not be null");
		}
		
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}
	
	@Override
	public Screen getCurrentScreen() {
		return screen;
	}

	
}
