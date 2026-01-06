package io.github.some_example_name;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PosEventTest {
    private Player testPlayer;
    @Before
    public void setUp()
    {
        testPlayer = new Player(145f, 70f, "TestPlayer", true);
    }

    @Test
    public void keyTest(){
        Key key = new Key(145, 70, true);
        if (!key.isCollected() && testPlayer.getPosition().dst(key.getPosition()) <= 16) {
            key.collect();
        }
        assertTrue(key.isCollected());

        Key farKey = new Key(200,200, true);
        if (!farKey.isCollected() && testPlayer.getPosition().dst(farKey.getPosition()) <= 16) {
            farKey.collect();
        }
        assertFalse(farKey.isCollected());
    }

    @Test
    public void clockTest(){
       Item testClock = new Item(145f, 70f, "TestClock", true);
       testClock.update(testPlayer);
       assertTrue(testClock.isCollected());
    }
}
