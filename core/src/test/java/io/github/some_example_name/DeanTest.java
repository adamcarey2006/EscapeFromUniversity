package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//Test ID 1
public class DeanTest extends HeadlessTest {

    private Dean dean;
    private Player player;

    private WinScreen winScreen;
    private MyGame testGame;
    private int[] achLog;

    @Before
    public void setUp() {
        player = new Player(175f, 45f, "test", true);
        dean = new Dean(90f, 450f, player);

        testGame = new MyGame();
        achLog = new int[5];
        winScreen = new WinScreen(testGame, 450, 120, 0, "DAN", achLog, true);
    }

    // 1.1
    @Test
    public void deanSpawnPosition() {
        Vector2 currentPos = dean.getPosition();
        assertEquals(90f, currentPos.x, 0.001f);
        assertEquals(450f, currentPos.y, 0.001f);
    }

    // 1.2
    @Test
    public void testDeanMovesTowardPlayer() {
        Vector2 before = new Vector2(dean.getPosition());

        dean.update(1f);

        Vector2 after = dean.getPosition();

        assertTrue(after.dst(player.getPosition()) < before.dst(player.getPosition()));
    }

    // 1.3
    @Test
    public void testResetToStartEvenCatch() {
        dean.resetToStart(2); // even

        Vector2 pos = dean.getPosition();
        assertEquals(90f, pos.x, 0.01f);
        assertEquals(450f, pos.y, 0.01f);
    }

    // 1.4
    @Test
    public void testResetToStartOddCatch() {
        dean.resetToStart(1); // odd

        Vector2 pos = dean.getPosition();
        assertEquals(390f, pos.x, 0.01f);
        assertEquals(400f, pos.y, 0.01f);
    }

    // 1.5
    @Test
    public void avoidDeanAchievement() {
        assertTrue(achLog[0] == 0);
        assertFalse(achLog[0] != 0);
    }

}
