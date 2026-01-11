package io.github.some_example_name;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HidEventTest extends HeadlessTest {

    private Player testPlayer;

    @Before
    public void setUp() {
        testPlayer = new Player(145f, 70f, "TestPlayer", true);
    }

    // 7.1
    @Test
    public void ticketTest() {

        BusTicket busTicket = new BusTicket(145f, 70f);

        if (!busTicket.isCollected()) {
            if (testPlayer.getPosition().dst(busTicket.getPosition()) < 16) {
                busTicket.discover();
                busTicket.collect();
            }
        }

        assertTrue(busTicket.isCollected());

        BusTicket farTicket = new BusTicket(145f, 150f);

        if (!farTicket.isCollected()) {
            if (testPlayer.getPosition().dst(farTicket.getPosition()) < 16) {
                farTicket.discover();
                farTicket.collect();
            }
        }

        assertFalse(farTicket.isCollected());
    }

}
