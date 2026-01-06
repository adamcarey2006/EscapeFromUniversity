package io.github.some_example_name;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//Test ID 2
public class GameTimerTest {

    private GameTimer timer;

    @Before
    public void setUp() {
        timer = new GameTimer(300f);
    }
    //2.1
    @Test
    public void timerInitialiseTest() {
        assertEquals(300f, timer.getTimeLeft(), 0.001f);
    }
    //2.2
    @Test
    public void timerDecrementsTest() {
        timer.decrementTimer(60f);
        assertEquals(240f, timer.getTimeLeft(), 0.001f);
    }
    //2.3
    @Test
    public void timerIncrementsTest() {
        timer.decrementTimer(60f);
        timer.incrementTimer(30f);
        assertEquals(270f, timer.getTimeLeft(), 0.001f);
    }
    //2.4
    @Test
    public void timerExceedMaxTest(){
        timer.incrementTimer(500f);
        assertEquals(300f, timer.getTimeLeft(), 0.001f);
    }
    //2.5
    @Test
    public void timerBelowZeroTest(){
        timer.decrementTimer(500f);
        assertEquals(0f, timer.getTimeLeft(), 0.001f);
    }
    //2.6
    @Test
    public void timerFormatTest(){
        timer.decrementTimer(60f);
        assertEquals("04 : 00", timer.toString());
    }
}

