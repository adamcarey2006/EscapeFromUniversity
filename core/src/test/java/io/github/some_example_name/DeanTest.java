package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeanTest {

    private Dean dean;
    private Player player;

    @Before
    public void setUp(){
        player = new Player(175f, 45f, "test", true);
        dean = new Dean(90f, 450f, player, true);
    }

    @Test
    public void deanSpawnPosition(){
        Vector2 currentPos = dean.getPosition();
        assertEquals(90f, currentPos.x, 0.001f);
        assertEquals(450f, currentPos.y, 0.001f);
    }
}
