package org.zezutom.android.gaming.framework;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAssetManager;
import org.robolectric.shadows.ShadowAudioManager;
import org.zezutom.android.gaming.framework.impl.AndroidAudio;

/**
 * Created by tom on 31/05/2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AndroidAudioTest {

    private Audio audio;

    @Before
    public void setUp() {
        Activity activity = Robolectric.newInstanceOf(Activity.class);
        audio = new AndroidAudio(activity);
    }

    // TODO add successful scenarios (can be achieved with mocked IO operations??)

    @Test(expected = RuntimeException.class)
    public void createMusicNonExistentFile() {
        audio.createMusic("invalid.mp3");
    }

    @Test(expected = RuntimeException.class)
    public void createSoundNonExistentFile() {
        audio.createSound("invalid.mp3");
    }

}
