package org.zezutom.android.gaming.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Just to test it works. To be deleted.
 *
 * Created by tom on 25/05/2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class FakeTest {

    @Test
    public void trueHoldsTrue() {
        assertEquals(true, true);
    }
}
