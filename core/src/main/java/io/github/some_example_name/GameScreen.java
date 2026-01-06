package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * <code> GameScreen </code> implements the main gameplay logic and rendering as
 * one class,
 * to process user input, and redraw the frames and update the game asset states
 * as
 * the game progresses.
 *
 * @see com.badlogic.gdx.Screen Screen.
 */

public class GameScreen implements Screen {
	private final MyGame game;
	private boolean isPaused = false;
	private int[] achLog;

	TiledMap tiledMap;
	OrthogonalTiledMapRenderer mapRenderer;
	OrthographicCamera camera;
	FitViewport viewport;

	private SpriteBatch batch;
	private Player player;

	private final Stage uiStage;
	private final Table uiTable;
	private final Skin uiSkin;
	private final GameTimer gameTimer;

	private BusTicket busTicket;
	private Locker locker;
	private BitmapFont font;
	private boolean canPickUpTicket = false;

	private Rectangle busInteractionArea;
	private boolean canEndGame = false;

	private final int MAP_WIDTH = 640;
	private final int MAP_HEIGHT = 640;
	private final float SQRT_2_INV = 0.70710678118f;

	private Dean dean;
	private NPC friend;
	private NPC survey;
	private int timesCaughtByDean = 0;
	/** New feature: Track negative, positive and hidden events */
	private static int negativeEvents;
	private static int positiveEvents;
	private static int hiddenEvents;
	private NPC sign;
	private Item clock;
	private Item Zzzz;
	private int friendSpeechCount = 1;
	private boolean surveyTimePenaltyAdded = false;
	private boolean clockTimeBonusAdded = false;
	private boolean ZzzzTimePenaltyAdded = false;

	/**
	 * Constructor for <code> GameScreen </code>, using the game creator
	 * in <code> MyGame </code> to create all main game and UI assets.
	 *
	 * @param game Game creator.
	 */
	private Key key;
	private Barrier barrier;

	/**
	 * Constructor for <code> GameScreen </code>, using the game creator
	 * in <code> MyGame </code> to create all main game and UI assets.
	 *
	 * @param game Game creator.
	 */
	public GameScreen(MyGame game, String username, int[] achLog) {
		this.game = game;
		this.achLog = achLog;

		negativeEvents = 0;
		positiveEvents = 0;
		hiddenEvents = 0;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, MAP_WIDTH, MAP_HEIGHT);
		camera.zoom = 0.5f;
		camera.update();

		tiledMap = new TmxMapLoader().load("Tile Maps/Final Game Map - Maze.tmx");

		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);

		batch = new SpriteBatch();
		player = new Player(145, 70, username, false);
		locker = new Locker(495, 575);
		dean = new Dean(90, 450, player, this, false);
		friend = new NPC(560, 300, "NPC.png", "Hey " + player.getUsername()
				+ ",\nLooking for your bus ticket?\nWant to know where I last saw it?", true);
		sign = new NPC(175, 200, "signpost.png", "North - Bus stop (exit)\nEast - Campus", true);

		survey = new NPC(560, 370, "NPC.png", "Hey!\nCan I get a moment of your" +
				"\ntime to take a quick survey?", false);
		clock = new Item(50, 40, "Clock.png");
		Zzzz = new Item(265, 560, "Clock.png");

		// Add Key and barrier near spawn
		key = new Key(470, 435);
		barrier = new Barrier(95, 450);

		font = new BitmapFont();

		MapObjects eventObjects = tiledMap.getLayers().get("Events").getObjects();

		MapObject ticketObject = eventObjects.get("BusTicket");
		if (ticketObject != null && ticketObject instanceof RectangleMapObject) {
			RectangleMapObject rect = (RectangleMapObject) ticketObject;
			busTicket = new BusTicket(rect.getRectangle().x, rect.getRectangle().y);
		}

		MapObject busObject = eventObjects.get("Bus");
		if (busObject != null && busObject instanceof RectangleMapObject) {
			this.busInteractionArea = ((RectangleMapObject) busObject).getRectangle();
		}

		uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		uiStage = new Stage(new FitViewport(MAP_WIDTH, MAP_HEIGHT));
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiStage.addActor(uiTable);
		gameTimer = new GameTimer(uiSkin, uiTable);
		uiTable.top().right().pad(10, 0, 0, 10);
	}

	/**
	 * Update game state from last frame, and render a new frame for the Screen
	 * using updated assets.
	 *
	 * @param delta Time in seconds since last frame finished rendering.
	 * @see com.badlogic.gdx.Screen#render Screen.render().
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleInput();

		if (isPaused) {
			// Render the current frame
			camera.update();
			mapRenderer.setView(camera);
			mapRenderer.render();

			batch.setProjectionMatrix(camera.combined);
			batch.begin();

			// Render the "Game Paused" message
			font.draw(batch, "Game Paused", camera.position.x - 50, camera.position.y + 50);

			// Render the player and other static elements
			player.render(batch);
			if (busTicket != null && busTicket.isCollected()) {
				busTicket.renderAsIcon(batch, camera);
			}

			batch.end();

			// Render the UI stage
			uiStage.act(delta);
			uiStage.draw();

			return; // Skip the rest of the game logic
		}

		/**
		 * NEW NPC changes:
		 * Updates "Friend" NPC speech based on conversation count.
		 * activates maths mini-game on 3rd interaction with the friend npc.
		 */
		// Updates NPC speech
		if (friend.manyTalks() > 1 && friend.manyTalks() > friendSpeechCount) {
			String name = player.getUsername();
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			String newSpeech = ("Best of luck!");
			if (friend.manyTalks() == 2) {
				newSpeech = ("Complete your homework & I'll tell you! \n Press E to begin.");
			} else if (friend.manyTalks() == 3) {
				game.setScreen(new EquationScreen(game, this, new Runnable() {
					@Override
					public void run() {
						friend.setSpeech("Try checking in the forest\n near your dorm room...");
						incrementHiddenEvents();
					}
				}));
			}
			friend.setSpeech(newSpeech);
			friendSpeechCount = friend.manyTalks();
		}

		// Decreases time if talked to survey NPC
		if (survey.isTalked() && !surveyTimePenaltyAdded) {
			gameTimer.decrementTimer(15f);
			surveyTimePenaltyAdded = true;
		}

		// Increases time if walked over clock
		if (clock.isCollected() && !clockTimeBonusAdded) {
			gameTimer.incrementTimer(30f);
			clockTimeBonusAdded = true;
			incrementPositiveEvents();
			clock.render(batch);
		}

		// Decreases time if walked over Zzzz
		if (Zzzz.isCollected() && !ZzzzTimePenaltyAdded) {
			gameTimer.decrementTimer(20f);
			ZzzzTimePenaltyAdded = true;
			incrementNegativeEvents();
			Zzzz.render(batch);
		}

		friend.update(player);
		sign.update(player);
		survey.update(player);
		clock.update(player);
		Zzzz.update(player);
		dean.update(delta);
		barrier.update(delta);

		if (player.getPosition().dst(dean.getPosition()) < 16f) {
			player.getPosition().set(145, 70);
			if (timesCaughtByDean == 0) {
				incrementNegativeEvents();
			}
			timesCaughtByDean++;
			dean.resetToStart(timesCaughtByDean); // send the dean back to his starting position or other side of the
													// map to ensure he can't spawn camp the player
		}

		locker.update(player, delta);

		if (busTicket != null) {
			if (!busTicket.isCollected()) {
				if (player.getPosition().dst(busTicket.getPosition()) < 16) {
					busTicket.discover();
					canPickUpTicket = true;
				} else {
					canPickUpTicket = false;
				}
			} else {
				Rectangle playerRect = new Rectangle(
						player.getPosition().x,
						player.getPosition().y,
						16,
						16);

				if (busInteractionArea != null &&
						playerRect.overlaps(busInteractionArea)) {
					canEndGame = true;
				} else {
					canEndGame = false;
				}
			}
		}
		// If you haven't collected the key and are within range, open the equation
		// screen
		/**
		 * NEW homework minigame called when trying to pickup the key
		 *
		 */
		if (!key.isCollected() && player.getPosition().dst(key.getPosition()) <= 16) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				game.setScreen(new EquationScreen(game, this, new Runnable() {
					@Override
					public void run() {
						key.collect();
					}
				}));
			}
		}

		// If you are near the locked barrier and have the key -> unlock it
		/**
		 * NEW Barrier event:
		 * Checks collision with the "Professor" barrier. Can be unlocked with the key.
		 */
		if (barrier.isLocked() && key.isCollected() && !key.isUsed()
				&& player.getPosition().dst(barrier.getPosition()) < 32) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				barrier.unlock();
				key.useKey();
			}
		}

		camera.position.set(player.getPosition().x, player.getPosition().y, 0);
		camera.update();

		mapRenderer.setView(camera);
		mapRenderer.render();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// switch to screen coordinates for the UI elements
		batch.setProjectionMatrix(uiStage.getCamera().combined);
		// pls refactor NOW
		font.draw(batch, "Positive Events Encountered = " + (positiveEvents) + "/4", 35, 630);
		font.draw(batch, "Negative Events Encountered = " + (negativeEvents) + "/5", 35, 610);
		font.draw(batch, "Hidden Event Encountered = " + (hiddenEvents) + "/3", 35, 590);

		key.renderUI(batch, 35, 500);

		// switch back to the game coordinates for game objects
		batch.setProjectionMatrix(camera.combined);

		if (locker.isBoostActive()) {
			achLog[2] = 1;
		}
		if (friend.isTalked() && survey.isTalked()) {
			achLog[3] = 1;
		}

		if (busTicket != null) {
			busTicket.render(batch);
		}

		if (canPickUpTicket) {
			font.draw(
					batch,
					"Press E to pick up",
					player.getPosition().x - 50,
					player.getPosition().y + 30);
		}

		if (canEndGame) {
			font.draw(
					batch,
					"Press E to use ticket",
					player.getPosition().x - 50,
					player.getPosition().y + 30);
		}

		// Messages will appear on top by rendering player last.
		locker.render(batch);
		dean.render(batch);
		friend.render(batch);
		sign.render(batch);
		survey.render(batch);
		clock.render(batch);
		Zzzz.render(batch);
		key.render(batch);
		barrier.render(batch);
		player.render(batch);

		if (busTicket != null && busTicket.isCollected()) {
			busTicket.renderAsIcon(batch, camera);
		}

		batch.end();

		// Decrement the timer only if the game is not paused
		if (!isPaused) {
			gameTimer.decrementTimer(delta);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new MenuScreen(game, achLog));
		}

		if (gameTimer.getTimeLeft() == 0) {
			gameTimer.onTimeUp();
			game.setScreen(new GameOverScreen(game, achLog));
		}
		uiStage.act(delta);
		uiStage.draw();
	}

	/**
	 * Move the player and interacting with the world and menus every frame when
	 * the corrosponding keys are pressed:
	 * <ul>
	 * <li>WASD - Move Character Up/Left/Down/Right.</li>
	 * <li>E - Interact with items.</li>
	 * <li>Esc - Pause Game.</li>
	 * </ul>
	 */
	private void handleInput() {
		float moveSpeed = 3f;
		if (locker != null && locker.isBoostActive()) {
			moveSpeed = 5f;
		}

		float newX = player.getPosition().x;
		float newY = player.getPosition().y;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			float originalSpeed = moveSpeed;
			if (Gdx.input.isKeyPressed(Input.Keys.A) | Gdx.input.isKeyPressed(Input.Keys.D)) {
				moveSpeed = moveSpeed * SQRT_2_INV;
			}
			newY += moveSpeed;
			player.setDirection(Player.Direction.UP);
			moveSpeed = originalSpeed;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			float originalSpeed = moveSpeed;
			if (Gdx.input.isKeyPressed(Input.Keys.A) | Gdx.input.isKeyPressed(Input.Keys.D)) {
				moveSpeed = moveSpeed * SQRT_2_INV;
			}
			newY -= moveSpeed;
			player.setDirection(Player.Direction.DOWN);
			moveSpeed = originalSpeed;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			newX -= moveSpeed;
			player.setDirection(Player.Direction.LEFT);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			newX += moveSpeed;
			player.setDirection(Player.Direction.RIGHT);
		}

		if (canPickUpTicket && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			busTicket.collect();
			incrementHiddenEvents();
			canPickUpTicket = false;
		}

		// Change pause functionality to use the P key, include this in docstrings
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			isPaused = !isPaused; // Toggle pause state
			return; // Skip other input handling when toggling pause
		}

		if (isPaused) {
			return; // Do not process input if the game is paused
		}

		else if (canEndGame && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			int finalScore = calculateFinalScore();
			int timeRemaining = (int) gameTimer.getTimeLeft();
			int timesCaught = getTimesCaughtByDean();
			if (timesCaught == 0) {
				achLog[0] = 1;
			}
			// send the player to the win screen
			game.setScreen(
					new WinScreen(game, finalScore, timeRemaining, timesCaught, player.getUsername(), achLog, false));
		}

		if (!isCellBlocked(newX, newY)) {
			// Only update position if you aren't colliding with barrier
			// Create 'hitbox' for player & check if touching barrier.
			boolean collision = false;
			if (barrier.isLocked()) {
				Rectangle newPlayerBounds = new Rectangle(newX, newY, 16, 16); // Player is 16x16
				if (newPlayerBounds.overlaps(barrier.getBounds())) {
					collision = true;
					barrier.showMessage();
				}
			} // Allow movement
			if (!collision) {
				player.getPosition().set(newX, newY);
			}
		}
	}

	/**
	 * Returns if the cell at a given coordinate in the world allows an entity
	 * to move onto it.Useful for checking collisions when moving player or another
	 * entity.
	 *
	 * @param x Horizontal position
	 * @param y Vertical position
	 * @return True if cell blocks entities to move onto it, False if entities can
	 *         move onto it.
	 */
	public boolean isCellBlocked(float x, float y) {
		for (int i = 0; i < tiledMap.getLayers().getCount(); i++) {
			if (tiledMap.getLayers().get(i) instanceof TiledMapTileLayer) {
				TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(i);
				int tileX = (int) ((x + 8) / layer.getTileWidth());
				int tileY = (int) ((y + 8) / layer.getTileHeight());
				TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

				if (cell != null && cell.getTile() != null) {
					if (cell.getTile().getProperties().containsKey("collidable")
							|| layer.getProperties().containsKey("collidable")) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Calculate the score after winning.
	 */
	public int calculateFinalScore() {

		// Uses time to determine score
		int timeRemainingSeconds = (int) gameTimer.getTimeLeft();

		int minutes = (int) (timeRemainingSeconds / 60);
		int seconds = (int) (timeRemainingSeconds % 60);
		int timeScore = (minutes * 100) + seconds; // this means 3:24 left on the clock gives a score of 324 before
													// penalties

		int penalties = timesCaughtByDean * 5; // 5 marks taken off per time caught

		int finalScore = timeScore - penalties;
		return Math.max(0, finalScore);
	}

	/**
	 * Resize UI and game map viewports when the window size is changed.
	 *
	 * @param width  Current width of window.
	 * @param height Current height of window.
	 * @see com.badlogic.gdx.Screen#resize Screen.resize().
	 */
	@Override
	public void resize(int width, int height) {
		uiStage.getViewport().update(width, height, true);
		uiStage.getViewport().apply();
		viewport.update(width, height);
		viewport.apply();
	}

	/**
	 * Get the number of times the player is caught by the Dean
	 */
	public int getTimesCaughtByDean() {
		return timesCaughtByDean;
	}

	public static void incrementPositiveEvents() {
		positiveEvents++;
	}

	/**
	 * NEW static method to increment negative events from other classes.
	 */
	public static void incrementNegativeEvents() {
		negativeEvents++;
	}

	public static void incrementHiddenEvents() {
		hiddenEvents++;
	}

	/**
	 * Dipose of all assets and UI elements when game screen is left i.e.
	 * when the player wins the game or quits.
	 *
	 * @see com.badlogic.gdx.Screen#dispose Screen.dispose().
	 */
	@Override
	public void dispose() {
		tiledMap.dispose();
		mapRenderer.dispose();
		batch.dispose();
		player.dispose();
		locker.dispose();
		font.dispose();
		uiStage.dispose();
		dean.dispose();
		friend.dispose();
		survey.dispose();
		sign.dispose();
		if (busTicket != null) {
			busTicket.dispose();
		}
		key.dispose();
		barrier.dispose();
	}

	public void addTime() {
		gameTimer.incrementTimer(30f);
	}

	/** Unimplemented */
	@Override
	public void show() {
	}

	/** Unimplemented */
	@Override
	public void pause() {
	}

	/** Unimplemented */
	@Override
	public void resume() {
	}

	/** Unimplemented */
	@Override
	public void hide() {
	}
}
