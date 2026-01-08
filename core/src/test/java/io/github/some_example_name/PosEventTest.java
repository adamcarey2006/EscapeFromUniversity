package io.github.some_example_name;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PosEventTest extends HeadlessTest {
    private Player testPlayer;

    @Before
    public void setUp() {
        testPlayer = new Player(145f, 70f, "TestPlayer");
    }

    // 5.1
    @Test
    public void keyTest() {
        Key key = new Key(145, 70);
        if (!key.isCollected() && testPlayer.getPosition().dst(key.getPosition()) <= 16) {
            key.collect();
        }
        assertTrue(key.isCollected());

        Key farKey = new Key(200, 200);
        if (!farKey.isCollected() && testPlayer.getPosition().dst(farKey.getPosition()) <= 16) {
            farKey.collect();
        }
        assertFalse(farKey.isCollected());
    }

    // 5.2
    @Test
    public void clockTest() {
        Item testClock = new Item(145f, 70f, "TestClock", true);
        testClock.update(testPlayer);
        assertTrue(testClock.isCollected());
    }

    // 5.3
    @Test
    public void friendTest() {
        NPC friend = new NPC(145, 80, "NPC.png", "Hey " + testPlayer.getUsername()
                + ",\nLooking for your bus ticket?\nWant to know where I last saw it?", true);
        friend.update(testPlayer);
        assertTrue(friend.isTalked());

        NPC farFriend = new NPC(560, 300, "NPC.png", "Hey " + testPlayer.getUsername()
                + ",\nLooking for your bus ticket?\nWant to know where I last saw it?", true);
        farFriend.update(testPlayer);
        assertFalse(farFriend.isTalked());

    }
}
