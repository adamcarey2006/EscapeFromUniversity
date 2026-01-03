package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;

// New imports for stage & textbox:
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * <code> MenuScreen </code> implements a main menu screen, to let player pause,
 * resume
 * and quit game.
 *
 * @see com.badlogic.gdx.Screen Screen.
 */

public class MenuScreen implements Screen {
	private final MyGame game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private FitViewport viewport;
	private int[] achLog;

	private Stage stage;
	private Skin skin;
	private TextField usernameField;

	private final int MENU_WIDTH = 640;
	private final int MENU_HEIGHT = 480;

	/**
	 * Constructor for <code> MenuScreen </code>, using the game creator in
	 * <code> MyGame </code> to create menu screen.
	 *
	 * @param game Game creator.
	 */
	public MenuScreen(MyGame game, int[] achLog) {
		this.game = game;
		this.achLog = achLog;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);

		batch = new SpriteBatch();
		font = new BitmapFont(); // uses default font of Arial
		font.getData().setScale(2f); // this makes the text bigger

		viewport = new FitViewport(MENU_WIDTH, MENU_HEIGHT, camera);
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		stage = new Stage(viewport); // sets viewport for stage

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		Label nameLabel = new Label("Enter Username:", skin);
		usernameField = new TextField(game.getUsername(), skin);
		table.add(nameLabel).padBottom(10);
		table.row();
		table.add(usernameField).width(250).height(41);
	}

	/**
	 * Show main menu screen.
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Renders the main menu screen & subsequent screens upon input.
	 *
	 * @param delta Time in seconds since last frame finished rendering.
	 * @see com.badlogic.gdx.Screen#render Screen.render().
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		font.draw(batch, "Press TAB to see Achievements", 32, 85);
		font.draw(batch, "Escape from University", 180, 375);
		font.draw(batch, "Press SPACE to Start", 32, 145);
		font.draw(batch, "Press ESC to Exit", 32, 115);
		font.draw(batch, "Leaderboard:", 380, 200);
		// Get leaderboard values & render
		java.util.List<java.util.Map.Entry<String, Integer>> leaderboard = game.getLeaderboard();
		if (leaderboard != null) {
			int y = 170;
			for (int i = 0; i < Math.min(leaderboard.size(), 5); i++) { // Only 5 rows are displayed
				java.util.Map.Entry<String, Integer> entry = leaderboard.get(i);
				font.draw(batch, (i + 1) + ". " + entry.getKey() + ": " + entry.getValue(), 380, y);// Seperates
																									// username & score
																									// (key & value)
				y -= 30;
			}
		}
		batch.end();

		stage.act(delta);// Update/render stage
		stage.draw();
		// Handling user inputs
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			String username = usernameField.getText();
			if (username.isEmpty()) {
				username = "John Pork";
			}
			game.setUsername(username);
			game.setScreen(new TutorialScreen(game, username, achLog));
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
			game.setUsername(usernameField.getText());
			game.setScreen(new Achievement(game, achLog)); // Go to achievement screen, pass current username &
															// achievements
		}
	}

	/**
	 * Resize UI Viewport when the window size is changed.
	 *
	 * @param width  Current width of window.
	 * @param height Current height of window.
	 * @see com.badlogic.gdx.Screen#resize Screen.resize().
	 */
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	/**
	 * Dispose of assets
	 * 
	 * @see com.badlogic.gdx.Screen#dispose Screen.dispose().
	 */
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		stage.dispose();
		skin.dispose();
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
		Gdx.input.setInputProcessor(null);
	}
}
