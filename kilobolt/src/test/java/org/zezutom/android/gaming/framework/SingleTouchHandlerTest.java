package org.zezutom.android.gaming.framework;

import android.view.MotionEvent;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.zezutom.android.gaming.framework.impl.SingleTouchHandler;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by tom on 31/05/2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class SingleTouchHandlerTest {

    public static final float SCALE_X = 1.0f;

    public static final float SCALE_Y = 1.0f;

    private final MotionEvent MOTION_EVENT = MotionEvent
                                                .obtain(100, 200,
                                                MotionEvent.ACTION_MOVE, 5f, 10f, 0);
    private TouchHandler touchHandler;

    @Before
    public void setUp() {
        View view = Robolectric.newInstanceOf(View.class);

        touchHandler = new SingleTouchHandler(view, SCALE_X, SCALE_Y);
        assertTrue(touchHandler.onTouch(view, MOTION_EVENT));
    }

    @Test
    public void isTouchDown() {
        TouchEvent touchEvent = assertTouchEvent();
        assertTrue(touchHandler.isTouchDown(touchEvent.getPosition().x));
        assertTrue(touchHandler.isTouchDown(touchEvent.getPosition().y));
        assertFalse(touchHandler.isTouchDown(touchEvent.getPointer()));
    }

    @Test
    public void getTouchEvents() {
        assertThat(assertTouchEvent().getType(), is(TouchType.DRAGGED));
    }

    private TouchEvent assertTouchEvent() {
        List<TouchEvent> touchEvents = touchHandler.getTouchEvents();

        if (touchEvents == null || touchEvents.isEmpty())
            fail("No touch events!");
        else if (touchEvents.size() != 1)
            fail("Exactly one touch event is expected!");

        TouchEvent touchEvent = touchEvents.get(0);

        if (touchEvent == null) fail("Touch event was null!");

        return touchEvent;
    }
}
