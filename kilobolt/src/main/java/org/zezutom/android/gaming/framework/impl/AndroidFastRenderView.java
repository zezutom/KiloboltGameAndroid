package org.zezutom.android.gaming.framework.impl;

import org.zezutom.android.gaming.framework.Screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
	
	public static final float DELTA_TIME_CAP = 3.15f;
	
	private AndroidGame game;
	
	private Bitmap frameBuffer;
	
	private Thread renderThread;
	
	private SurfaceHolder holder;
	
	private volatile boolean running = false;
	
	public AndroidFastRenderView(AndroidGame game, Bitmap frameBuffer) {
		super(game);
		this.game = game;
		this.frameBuffer = frameBuffer;
		this.holder = getHolder();
	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}

	public void pause() {
		running = false;
		
		while (true) {
			try {
				renderThread.join();
				break;
			} catch (InterruptedException e) {
				// retry
			}			
		}
		
	}

	@Override
	public void run() {
		Rect rect = new Rect();
		long startTime = System.nanoTime();
		
		while (running) {
			if (!holder.getSurface().isValid()) {
				continue;
			}
			
			// how much time elapsed since the last update (in milliseconds)
			float deltaTime = (System.nanoTime() - startTime) / 10000000.000f;
			startTime = System.nanoTime();
			
			// delta time cap - ensures smooth movement even if the game slows down
			if (deltaTime > DELTA_TIME_CAP) {
				deltaTime = DELTA_TIME_CAP;
			}
			
			Screen currentScreen = game.getCurrentScreen();
			currentScreen.update(deltaTime);
			currentScreen.paint(deltaTime);
			
			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(rect);
			canvas.drawBitmap(frameBuffer, null, rect, null);
			holder.unlockCanvasAndPost(canvas);
		}
	}

}
