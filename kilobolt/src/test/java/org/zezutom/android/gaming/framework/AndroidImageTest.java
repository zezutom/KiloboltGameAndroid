package org.zezutom.android.gaming.framework;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowBitmap;
import org.zezutom.android.gaming.framework.impl.AndroidImage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by tom on 31/05/2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AndroidImageTest {

    private static final Graphics.ImageFormat FORMAT = Graphics.ImageFormat.ARGB4444;

    // Making this static would cause java.lang.ExceptionInInitializerError
    private final Bitmap BITMAP = ShadowBitmap.createBitmap(10, 20, Bitmap.Config.ARGB_4444);

    private Image image;


    @Before
    public void setUp() {
        image = new AndroidImage(BITMAP, FORMAT);
        assertFalse(BITMAP.isRecycled());
    }

    @Test
    public void getWidth() {
        assertThat(image.getWidth(), is(BITMAP.getWidth()));
    }

    @Test
    public void getHeight() {
        assertThat(image.getHeight(), is(BITMAP.getHeight()));
    }

    @Test
    public void getFormat() {
        assertThat(image.getFormat(), is(FORMAT));
    }

    @Test
    public void dispose() {
        image.dispose();
        assertTrue(BITMAP.isRecycled());
    }
}
