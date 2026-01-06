package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player(145f,70f,"test", true);
    }

    @Test
    public void correctSpawnLocation() {
        Vector2 currentPos = player.getPosition();
        assertEquals(145f, currentPos.x, 0.001f);
        assertEquals(70f, currentPos.y, 0.001f);
    }

    @Test
    public void usernameStoredForLeaderboard() {
        assertEquals("test", player.getUsername());
    }

    @Test
    public void playerCanMoveUp() {
        Vector2 pos = player.getPosition();
        pos.y += 10;
        assertEquals(80f, pos.y, 0.001f);
    }

    @Test
    public void playerCanMoveDown() {
        Vector2 pos = player.getPosition();
        pos.y -= 10;
        assertEquals(60f, pos.y, 0.001f);
    }

    @Test
    public void playerCanMoveLeft() {
        Vector2 pos = player.getPosition();
        pos.x -= 10;
        assertEquals(135f, pos.x, 0.001f);
    }

    @Test
    public void playerCanMoveRight() {
        Vector2 pos = player.getPosition();
        pos.x += 10;
        assertEquals(155f, pos.x, 0.001f);
    }

}
