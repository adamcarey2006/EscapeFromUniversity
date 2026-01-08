package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * <code> Player </code> is the main character of the game, handling rendering
 * and direction of the character sprite.
 */
public class Player {
	/**
	 * Enumeration of all the possible directions the player sprite can face.
	 */
	public enum Direction {
		/** Face North */
		UP,

		/** Face South */
		DOWN,
		/** Face West */
		LEFT,
		/** Face East */
		RIGHT
	}

	private Vector2 position;
	private Texture frontTexture;
	private Texture backTexture;
	private Texture sideTexture;
	private TextureRegion frontFrame;
	private TextureRegion backFrame;
	private TextureRegion sideFrame;
	private TextureRegion currentFrame;
	/** NEW - used for leaderboard. */
	private String username;

	/**
	 * Constructor for <code> Player </code>, with a set of coordinates.
	 *
	 * @param x        Horizontal position for player to spawn in.
	 * @param y        Vertical position for player to spawn in.
	 * @param username Players username.
	 */
	public Player(float x, float y, String username) {
		this.username = username;
		position = new Vector2(x, y);

		if (com.badlogic.gdx.Gdx.app.getType() != com.badlogic.gdx.Application.ApplicationType.HeadlessDesktop) {
			frontTexture = new Texture("Player-front.png");
			backTexture = new Texture("Player-back.png");
			sideTexture = new Texture("Player-side.png");

			frontFrame = new TextureRegion(frontTexture);
			backFrame = new TextureRegion(backTexture);
			sideFrame = new TextureRegion(sideTexture);

			currentFrame = frontFrame;
		}
	}

	/**
	 * Get the player's username.
	 *
	 * @return username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Called to set the direction of the Player sprite.
	 *
	 * @param newDirection the direction the player should face.
	 * @see Direction Direction.
	 */
	public void setDirection(Direction newDirection) {
		// For automated testing, skips the rendering
		if (frontFrame == null)
			return;

		switch (newDirection) {
			case UP:
				currentFrame = backFrame;
				break;
			case DOWN:
				currentFrame = frontFrame;
				break;
			case LEFT:
				currentFrame = sideFrame;
				if (currentFrame.isFlipX()) {
					currentFrame.flip(true, false);
				}
				break;
			case RIGHT:
				currentFrame = sideFrame;
				if (!currentFrame.isFlipX()) {
					currentFrame.flip(true, false);
				}
				break;
		}
	}

	/**
	 * Convenience method to be called by the game screen's <code> render()
	 * </code> method, to draw the player using a SpriteBatch at the current
	 * player coordinates.
	 *
	 * @param batch SpriteBatch used by application to render all sprites.
	 * @see com.badlogic.gdx.graphics.g2d.SpriteBatch SpriteBatch
	 * @see com.badlogic.gdx.Screen#render Screen.render().
	 */
	public void render(SpriteBatch batch) {
		if (currentFrame == null)
			return;
		batch.draw(currentFrame, position.x, position.y);
	}

	/**
	 * Get player position in world.
	 *
	 * @return The players x and y coordinates (vector).
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Dispose of textures
	 *
	 * @see com.badlogic.gdx.Screen#dispose Screen.dispose().
	 */
	public void dispose() {
		if (frontTexture != null)
			frontTexture.dispose();
		if (backTexture != null)
			backTexture.dispose();
		if (sideTexture != null)
			sideTexture.dispose();
	}
}
