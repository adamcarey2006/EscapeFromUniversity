package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * <code> Item </code> represents the item events used to change the timer
 * , containing both the actual sprite the user can interact with
 * , and UI elements to represent if the item has been walked over or not.
 */
public class Item {
    private Vector2 position;
    private Texture texture;
    private boolean isCollected;

    /**
     * Constructor for <code> Item </code>, spawning the item sprite
     * in the given world coordinates.Uses given sprite in
     * <code> assets/ </code> folder for sprite.
     *
     * @param x Horizontal position in world for item sprite.
     * @param y Vertical position in world for item sprite.
     * @param sprite String value containing sprites file name.
     */
    public Item(float x, float y, String sprite) {
        this.position = new Vector2(x, y);
        this.texture = new Texture(sprite);
        this.isCollected = false;
    }

    /**
     * Update icon is shown to player depending on player position
     * and if the item has been walked over.
     *
     * @param player Player object.
     */
    public void update(Player player) {
        if (player.getPosition().dst(position) < 10f && !isCollected) {
            isCollected = true;
        }
    }

    /**
     * Render item sprite within given sprite batch.
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
     * Return item position as a 2D Vector.
     *
     * @return 2D Vector with x/y positions of clock.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Dispose item sprite texture.Use as part of
     * Application dispose method.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Return if the item has been collected by player or not.
     *
     * @return True/False value corrosponding to if item is collected.
     */
    public boolean isCollected() {
        return isCollected;
    }

}
