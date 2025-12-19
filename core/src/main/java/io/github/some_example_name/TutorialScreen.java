package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TutorialScreen implements Screen {
    private final MyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture tutorialImage;
    private FitViewport viewport;
    private int[] achLog;

    private final int MENU_WIDTH = 790;
    private final int MENU_HEIGHT = 480;
    private String username;

    public TutorialScreen(MyGame game, String username,int[] achLog) {
        this.game = game;
        this.username = username;
        this.achLog = achLog;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        batch = new SpriteBatch();

        tutorialImage = new Texture("How to Play Screen.png");

        viewport = new FitViewport(MENU_WIDTH, MENU_HEIGHT, camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(tutorialImage, 0, 0, MENU_WIDTH, MENU_HEIGHT);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game, username, achLog));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        tutorialImage.dispose();
    }

    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

}
