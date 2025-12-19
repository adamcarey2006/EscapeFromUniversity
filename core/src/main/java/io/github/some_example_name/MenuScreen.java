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
	public MenuScreen(MyGame game,int[] achLog) {
		this.game = game;
        this.achLog = achLog;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);

		batch = new SpriteBatch();
		font = new BitmapFont(); // uses default font of Arial
		font.getData().setScale(2f); // this makes the text bigger

		viewport = new FitViewport(MENU_WIDTH, MENU_HEIGHT, camera);
		// New code for stage & textbox:
		skin = new Skin(Gdx.files.internal("ui/uiskin.json")); // loads the skin file

		// Programmatically generate resources for TextField since uiskin.json/atlas
		// lacks them
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.font = skin.getFont("PIXELFONT");
		textFieldStyle.fontColor = Color.WHITE;
		textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
		textFieldStyle.background = skin.newDrawable("white", Color.DARK_GRAY);

		skin.add("default", textFieldStyle);

		stage = new Stage(viewport); // sets viewport for stage

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		Label nameLabel = new Label("Enter Username:", skin);// creates label
		usernameField = new TextField("", skin);
		table.add(nameLabel).padBottom(10);// place label on stage
		table.row();// place text field on stage
		table.add(usernameField).width(200);// place text field on stage
	}

	/**
	 * Show main menu screen.
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Process input then render new frame for the main menu.
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

		// Display Leaderboard
		font.draw(batch, "Leaderboard:", 380, 200);
		java.util.List<java.util.Map.Entry<String, Integer>> leaderboard = game.getLeaderboard();// gets the leaderboard
		if (leaderboard != null) {
			int y = 170;// y position of the first score
			for (int i = 0; i < Math.min(leaderboard.size(), 5); i++) {// displays the top 5 scores
				java.util.Map.Entry<String, Integer> entry = leaderboard.get(i);// gets the entry at the current index
				font.draw(batch, (i + 1) + ". " + entry.getKey() + ": " + entry.getValue(), 380, y);// display the score
				y -= 30;// places the next score below the previous one
			}
		}
		batch.end();

		stage.act(delta);// update + draw stage
		stage.draw();

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			String username = usernameField.getText();
			if (username.isEmpty()) {
				username = "Mark"; // Default username
			}
			game.setScreen(new TutorialScreen(game, username, achLog));// go to tutorial screen
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
			game.setScreen(new Achievement(game, achLog));
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
	 * Dispose menu assets when menu is exited or program is quit.
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
