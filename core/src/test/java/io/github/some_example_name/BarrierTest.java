package io.github.some_example_name;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.badlogic.gdx.math.Vector2;

public class BarrierTest extends HeadlessTest {

    private Barrier testBarrier;

    // Constants for barrier size
    private static final float BARRIER_X = 50f;
    private static final float BARRIER_Y = 50f;

    @Before
    public void setUp() {
        // Create barrier before testing
        testBarrier = new Barrier(BARRIER_X, BARRIER_Y);
    }

    @Test
    public void newlyCreatedBarrier_ShouldBeLockedAndAtCorrectPosition() {
        // Verify creation, position & that it persists in the intended locked state
        assertNotNull("Barrier should be created successfully", testBarrier);

        Vector2 expectedPosition = new Vector2(BARRIER_X, BARRIER_Y);
        assertEquals("Barrier position should match the coordinates provided",
                expectedPosition, testBarrier.getPosition());
        assertTrue("Barrier should be locked when created", testBarrier.isLocked());
    }

    @Test
    public void unlockingBarrier_ShouldChangeStatusToUnlocked() {
        testBarrier.unlock();
        assertFalse("For barrier, islocked() should be false after unlock() is called",
                testBarrier.isLocked());
    }
}
