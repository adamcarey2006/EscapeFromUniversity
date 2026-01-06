package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * <code> Barrier </code> Negative event that blocks the player - destroyed with
 * key.
 * NEW : Professor blocking path negative event logic.
 */
public class Barrier {
    private boolean showMessage = false;
    private float messageTimer = 0f;

    private Vector2 position;
    private Texture texture;
    private boolean isLocked;
    private Rectangle bounds;
    private BitmapFont font;
    private boolean firstTime = true;

    /**
     * <code> Barrier </code> constructor.
     * 
     * @param x Position X
     * @param y Position Y
     */
    public Barrier(float x, float y) {
        this.position = new Vector2(x, y);
        this.texture = new Texture("Dean-front.png");
        this.isLocked = true;
        this.bounds = new Rectangle(x, y, 16, 16); // barrier size
        this.font = new BitmapFont();
    }

    public void render(SpriteBatch batch) {
        if (isLocked) {
            batch.draw(texture, position.x, position.y, 16, 16);
            if (showMessage) {
                if (firstTime) {
                    firstTime = false;
                    GameScreen.incrementNegativeEvents();
                }
                font.draw(batch, "Halt. You'll need a doctor's\nnote to be excused early.", position.x - 40,
                        position.y + 50);
            }
        }
    }

    /**
     * Show messages if barrier is locked.
     * 
     * @param delta Time.
     */
    public void update(float delta) {
        if (showMessage) {
            messageTimer += delta;
            if (messageTimer > 2f) {
                showMessage = false;
                messageTimer = 0f;
            }
        }
    }

    public void showMessage() {
        showMessage = true;
        messageTimer = 0f;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void unlock() {
        isLocked = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}
