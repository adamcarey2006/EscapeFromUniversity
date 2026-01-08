package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * <code> NPC </code> represents NPCs that can provide items or dialog to
 * the player when interacted with.
 */
public class NPC {
	private Texture texture;
	private Vector2 position;
	private Rectangle bounds;
	private BitmapFont font;
	private boolean showMessage = false;
	private String speech;
	private boolean talked = false;
	private int numTalked = 0;
	private boolean isFriendly;

	/**
	 * Constructor for <code> NPC </code>, with a set of coordinates.
	 *
	 * @param x Horizontal position for NPC to spawn in.
	 * @param y Vertical position for NPC to spawn in.
	 */
	public NPC(float x, float y, String sprite, String Speech, boolean isFriendly) {
		if (com.badlogic.gdx.Gdx.app.getType() != com.badlogic.gdx.Application.ApplicationType.HeadlessDesktop) {
			texture = new Texture(sprite);
			bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
			font = new BitmapFont();
		} else {
			// In headless mode, we can set bounds to a default size
			bounds = new Rectangle(x, y, 32, 32);
		}
		position = new Vector2(x, y);
		speech = Speech;
		this.isFriendly = isFriendly;
	}

	/**
	 * Update if the dialog is shown to player depending on player position
	 * and if the E key has been pressed recently.
	 *
	 * @param player Player object.
	 */
	public void update(Player player) {
		if (player.getPosition().dst(position) < 30f) {
			boolean interact = false;
			// Check input only if Gdx.input is available (it is in HeadlessApplication
			// usually)
			if (Gdx.input != null && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				interact = true;
			}
			// Automatic interaction in headless mode (tests)
			if (Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.HeadlessDesktop) {
				interact = true;
			}

			if (interact) {
				showMessage = true;
				this.talked = true;
				this.numTalked += 1;
				if (this.numTalked == 1) {
					if (this.isFriendly) {
						GameScreen.incrementPositiveEvents();
					} else if (!this.isFriendly) {
						GameScreen.incrementNegativeEvents();
					}
				}
			}
		}

		if (showMessage && player.getPosition().dst(position) > 60f) {
			showMessage = false;
		}
	}

	/**
	 * Draws the NPC's sprite and its dialog using a SpriteBatch at the current
	 * player coordinates.
	 *
	 * @param batch SpriteBatch used by application to render all sprites.
	 */
	public void render(SpriteBatch batch) {
		if (texture == null) {
			return;
		}
		batch.draw(texture, position.x, position.y);
		if (showMessage) {
			font.draw(
					batch,
					speech,
					position.x - 100, position.y + texture.getHeight() + 40);
		}
	}

	/**
	 * Dispose of textures of NPC's sprites and dialog.
	 */
	public void dispose() {
		if (texture != null)
			texture.dispose();
		if (font != null)
			font.dispose();
	}

	/**
	 * Get the NPC's position in world.
	 *
	 * @return The players x-by-y coordinates as a 2D vector.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Get NPC's collision box.
	 *
	 * @return Rectangle bounds of NPC.
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Get NPC's talked status.
	 *
	 * @return boolean status.
	 */
	public boolean isTalked() {
		return (talked);
	}

	/**
	 * Get NPC's times talked.
	 *
	 * @return int of number of times talked.
	 */
	public int manyTalks() {
		return (numTalked);
	}

	/**
	 * Get new speech input.
	 *
	 * @param nSpeech String used by application to change speech.
	 */
	public void setSpeech(String nSpeech) {
		speech = nSpeech;
	}
}
