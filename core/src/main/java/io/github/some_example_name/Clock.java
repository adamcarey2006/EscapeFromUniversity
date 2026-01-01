package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * <code> Clock </code> represents the clock event used to add to the timer
 * , containing both the actual sprite the user can interact with
 * , and UI elements to represent if the clock has been walked over or not.
 */
public class Clock {
    private Vector2 position;
    private Texture texture;
    private boolean isCollected;

    /**
     * Constructor for <code> Clock </code>, spawning the clock sprite
     * in the given world coordinates.Uses "Clock.png" in
     * <code> assets/ </code> folder for sprite.
     *
     * @param x Horizontal position in world for clock sprite.
     * @param y Vertical position in world for clock sprite.
     */
    public Clock(float x, float y) {
        this.position = new Vector2(x, y);
        this.texture = new Texture("Clock.png");
        this.isCollected = false;
    }

    /**
     * Update icon is shown to player depending on player position
     * and if the clock has been walked over.
     *
     * @param player Player object.
     */
    public void update(Player player) {
        if (player.getPosition().dst(position) < 10f && !isCollected) {
            isCollected = true;
            GameScreen.incrementPositiveEvents();
        }
    }

    /**
     * Render clock sprite within given sprite batch.
     *
     * @param batch SpriteBatch to render world sprite in.
     * @see com.badlogic.gdx.graphics.g2d.SpriteBatch SpriteBatch.
     */
    public void render(SpriteBatch batch) {
        if (!isCollected) {
            batch.draw(texture, position.x, position.y, 11, 11);
        }
    }

    /**
     * Return clock position as a 2D Vector.
     *
     * @return 2D Vector with x/y positions of clock.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Dispose clock sprite texture.Use as part of
     * Application dispose method.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Return if the clock has been collected by player or not.
     *
     * @return True/False value corrosponding to if clock is collected.
     */
    public boolean isCollected() {
        return isCollected;
    }

}
