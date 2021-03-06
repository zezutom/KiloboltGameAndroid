package org.zezutom.android.gaming.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.zezutom.android.gaming.framework.FileIO;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

public class AndroidFileIO implements FileIO {

	private Context context;
	
	private AssetManager assetManager;
	
	private String externalStoragePath;
	
	public AndroidFileIO(Context context) {
		this.context = context;
		this.assetManager = context.getAssets();
		this.externalStoragePath = Environment
									.getExternalStorageDirectory()
									.getAbsolutePath() + File.separator;
	}

	@Override
	public InputStream readFile(String file) throws IOException {		
		return new FileInputStream(externalStoragePath + file);
	}

	@Override
	public OutputStream writeFile(String file) throws IOException {
		return new FileOutputStream(externalStoragePath + file);
	}

	@Override
	public InputStream readAsset(String file) throws IOException {
		return assetManager.open(file);
	}

	@Override
	public SharedPreferences getSharedPrefs() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
}
