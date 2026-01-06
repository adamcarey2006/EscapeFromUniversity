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

    @Test
    public void surveyTest() {
        NPC survey = new NPC(145f, 80f, "NPC.png", "Hey!\nCan I get a moment of your" +
            "\ntime to take a quick survey?", false, true);
        survey.update(testPlayer, true);
        assertTrue(survey.isTalked());

        NPC farSurvey = new NPC(145f, 150f, "NPC.png", "Hey!\nCan I get a moment of your" +
            "\ntime to take a quick survey?", false, true);
        farSurvey.update(testPlayer, true);
        assertFalse(farSurvey.isTalked());
    }

    @Test
    public void barrierTest() {
        Key key = new Key(145, 70, true);
        Barrier barrier = new Barrier(145f,70f, true);
        key.collect();
        if (barrier.isLocked() && key.isCollected() && !key.isUsed()
            && testPlayer.getPosition().dst(barrier.getPosition()) < 32) {
            barrier.unlock();
            key.useKey();
        }
        assertFalse(barrier.isLocked());
        assertTrue(key.isUsed());

        }

}
