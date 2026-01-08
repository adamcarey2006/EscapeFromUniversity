package io.github.some_example_name;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.badlogic.gdx.math.Vector2;

public class KeyTest extends HeadlessTest {

    private Key testKey;
    private static final float INITIAL_X = 100f;
    private static final float INITIAL_Y = 200f;

    @Before
    public void setUp() {
        testKey = new Key(INITIAL_X, INITIAL_Y);
    }

    @Test
    public void newlyCreatedKey_ShouldHaveCorrectPositionAndStatus() {
        // Verify creation, position & existence
        assertNotNull("Key should be created", testKey);

        Vector2 expectedPosition = new Vector2(INITIAL_X, INITIAL_Y);
        assertEquals("Key position should match the coordinates passed in",
                expectedPosition, testKey.getPosition());
        assertFalse("Key should not be marked as collected upon creation", testKey.isCollected());
        assertFalse("Key should not be marked as used upon creation", testKey.isUsed());
    }

    @Test
    public void collectingKey_ShouldUpdateStatusToCollected() {
        testKey.collect();
        assertTrue("Key should be marked as collected after calling collect()", testKey.isCollected());
        assertFalse("Key should be unused until collect() is called", testKey.isUsed());
    }

    @Test
    public void usingKey_ShouldUpdateStatusToUsed() {
        testKey.collect();
        testKey.useKey();
        assertTrue("Key should be marked as used after calling useKey()", testKey.isUsed());
    }
}
