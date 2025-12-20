package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Achievement implements Screen {
    private final MyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private FitViewport viewport;
    private int[] achLog;

    private final int MENU_WIDTH = 640;
    private final int MENU_HEIGHT = 480;


    /**
     * Constructor for <code> Achievement </code>, using the game creator in
     * <code> MyGame </code> to create Achievement screen.
     * @param game Game creator.
     */
    public Achievement(MyGame game, int[] achLog) {
        this.game = game;
        this.achLog = achLog;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);

        batch = new SpriteBatch();
        font = new BitmapFont(); //uses default font of Arial
        font.getData().setScale(2f); //this sets the text size

        viewport = new FitViewport(MENU_WIDTH, MENU_HEIGHT, camera);
    }

    /**
     * Show Achievement screen.
     */
    @Override
    public void show() {}

    /**
     * Process input then render new frame for the Achievement screen.
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
        font.draw(batch, "Achievements", 175, 350);
        font.draw(batch, "Avoided the dean.", 160, 275);
        if (achLog[0] == 1) {
            font.draw(batch, "Yes", 450, 275);
        } else if (achLog[0] == 0) {
            font.draw(batch, "No", 450, 275);
        }
        font.draw(batch, "Speedrunner", 160, 225);
        if (achLog[1] == 1) {
            font.draw(batch, "Yes", 450, 225);
        } else if (achLog[1] == 0) {
            font.draw(batch, "No", 450, 225);
        }
        font.draw(batch, "Sugar High", 160, 175);
        if (achLog[2] == 1) {
            font.draw(batch, "Yes", 450, 175);
        } else if (achLog[2] == 0) {
            font.draw(batch, "No", 450, 175);
        }
        font.draw(batch, "Friendly gamer", 160, 125);
        if (achLog[3] == 1) {
            font.draw(batch, "Yes", 450, 125);
        } else if (achLog[3] == 0) {
            font.draw(batch, "No", 450, 125);
        }
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game, achLog));
        }
    }

    /**
     * Resize UI Viewport when the window size is changed.
     * @param width Current width of window.
     * @param height Current height of window.
     * @see com.badlogic.gdx.Screen#resize Screen.resize().
     */
    @Override
    public void resize(int width, int height) { viewport.update(width,height); }

    /**
     * Dispose achievement assets when achievements is exited.
     * @see com.badlogic.gdx.Screen#dispose Screen.dispose().
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    /** Unimplemented */
    @Override
    public void pause() {}

    /** Unimplemented */
    @Override
    public void resume() {}

    /** Unimplemented */
    @Override
    public void hide() {}
}
