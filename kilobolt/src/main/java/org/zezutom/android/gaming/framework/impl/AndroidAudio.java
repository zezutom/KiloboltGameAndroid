package org.zezutom.android.gaming.framework.impl;

import java.io.IOException;

import org.zezutom.android.gaming.framework.Audio;
import org.zezutom.android.gaming.framework.Music;
import org.zezutom.android.gaming.framework.Sound;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

public class AndroidAudio implements Audio {

	private AssetManager assetManager;
	
	private SoundPool soundPool;
	
	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assetManager = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}

	@Override
	public Music createMusic(String file) {
		AssetFileDescriptor assetFileDescriptor;
		try {
			assetFileDescriptor = assetManager.openFd(file);
			return new AndroidMusic(assetFileDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + file + "'");
		}
		
	}

	@Override
	public Sound createSound(String file) {
		AssetFileDescriptor assetFileDescriptor;
		try {
			assetFileDescriptor = assetManager.openFd(file);
			int soundId = soundPool.load(assetFileDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
			
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load sound '" + file + "'");
		}				
	}

}
