package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * NEW feature: Item to be picked up which lets you past a barrier!
 */
public class Key {
    private Vector2 position;
    private Texture texture;
    private boolean pickedUp;
    private boolean used;

    /**
     * Constructor, creates key at position.
     * 
     * @param x X position
     * @param y Y position
     */
    public Key(float x, float y) {
        this.position = new Vector2(x, y);
        this.texture = new Texture("key.png");
        this.pickedUp = false;
        this.used = false;
    }

    /**
     * Render sprite in, if not picked up.
     * 
     * @param batch Render sprite.
     */
    public void render(SpriteBatch batch) {
        if (!pickedUp) {
            batch.draw(texture, position.x, position.y, 16, 16);
        }
    }

    /**
     * Render key png on the GUI
     * 
     * @param batch Render sprite.
     * @param x     X position
     * @param y     Y position
     */
    public void renderUI(SpriteBatch batch, float x, float y) {
        if (pickedUp && !used) {
            batch.draw(texture, x, y, 32, 32);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public boolean isCollected() {
        return pickedUp;
    }

    public void collect() {
        this.pickedUp = true;
    }

    public void useKey() {
        this.used = true;
    }

    public boolean isUsed() {
        return used;
    }

    public void dispose() {
        texture.dispose();
    }
}
