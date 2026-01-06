package io.github.some_example_name;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NegEventTest {

    private Player testPlayer;

    @Before
    public void setUp()
    {
        testPlayer = new Player(145f, 70f, "TestPlayer", true);
    }

    @Test
    public void sleepTest() {
        Item testSleep = new Item(145f, 70f, "Zzz", true);
        testSleep.update(testPlayer);
        assertTrue(testSleep.isCollected());
    }

}
