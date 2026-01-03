package io.github.some_example_name;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WinScreenTest {

    private WinScreen winScreen;
    private MyGame testGame;
    private int[] achLog;

    @Before
    public void setUp() {
        testGame = new MyGame();
        achLog = new int[5];
        winScreen = new WinScreen(testGame, 450, 120, 2, "DAN", achLog, true);
    }

    @Test
    public void finalScoreTest(){
        assertEquals(450, winScreen.finalScore);
    }

    @Test
    public void achievementFlagTest(){
        assertEquals(1, achLog[1]);
    }

    @Test
    public void noAchievementFlagTest(){
        int[] tmpAchLog = new int[5];
        WinScreen tmpScreen = new WinScreen(testGame, 300, 120, 1, "BOB", tmpAchLog, true);
        assertEquals(0, tmpAchLog[1]);
    }
}
