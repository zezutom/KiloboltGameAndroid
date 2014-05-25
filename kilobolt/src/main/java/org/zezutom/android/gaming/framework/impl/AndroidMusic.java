package org.zezutom.android.gaming.framework.impl;

import org.zezutom.android.gaming.framework.Music;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

import java.io.IOException;

public class AndroidMusic implements Music, OnCompletionListener, OnSeekCompleteListener, OnPreparedListener, OnVideoSizeChangedListener {

	private MediaPlayer mediaPlayer;
	
	private boolean prepared = false;
	
	public AndroidMusic(AssetFileDescriptor assetFileDescriptor) {
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer
				.setDataSource(
						assetFileDescriptor.getFileDescriptor(), 
						assetFileDescriptor.getStartOffset(), 
						assetFileDescriptor.getLength());
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music");
		}
	}

	@Override
	public void play() {
		if (mediaPlayer.isPlaying()) {
			return;
		}
		
		try {
			synchronized (this) {
				if (!prepared) {
					mediaPlayer.prepare();
					mediaPlayer.start();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Media player won't play");
		}		
	}

	@Override
	public void stop() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		
		synchronized (this) {
			prepared = false;
		}
	}

	@Override
	public void pause() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	@Override
	public void setLooping(boolean looping) {
		mediaPlayer.setLooping(looping);

	}

	@Override
	public void setVolume(float volume) {
		mediaPlayer.setVolume(volume, volume);

	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped() {
		return !prepared;
	}

	@Override
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	@Override
	public void dispose() {
		try {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}			
		} finally {
			mediaPlayer.release();	
		}		
	}

	@Override
	public void seekBegin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		synchronized (this) {
			prepared = false;
		}		
	}

}
