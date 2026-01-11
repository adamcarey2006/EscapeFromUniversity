package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;

/**
 * Display math equation and check answer. Mostly reused from GameScreen.
 * New feature: "Homework" mini-game. Must be solved to proceed!
 */
public class EquationScreen implements Screen {
    private final MyGame game;
    private final Screen parentScreen;
    private Runnable onSuccess;
    private Stage stage;
    private Skin skin;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextField answerField;
    private Label resultLabel;

    private final int SCREEN_WIDTH = 640;
    private final int SCREEN_HEIGHT = 480;
    private int a;
    private int b;
    private boolean addition;
    private int correctAnswer;

    public EquationScreen(MyGame game, Screen parentScreen, Runnable onSuccess) {
        // Setup camera & stage.
        this.game = game;
        this.parentScreen = parentScreen;
        this.onSuccess = onSuccess;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        // Ui setup
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Random random = new Random();
        this.a = random.nextInt(10) + 3;
        this.b = random.nextInt(10);
        if (random.nextBoolean()) {
            this.addition = true;
            this.correctAnswer = a + b;
        } else {
            // Swap to ensure the answer is positive as we filtered out input to be an
            // integer
            if (a < b) {
                int temp = a;
                a = b;
                b = temp;
            }
            this.correctAnswer = a - b;
            this.addition = false;
        }
        String operator = addition ? "+" : "-";
        Label questionLabel = new Label("" + a + " " + operator + " " + b + " = ?", skin);

        questionLabel.setFontScale(2f);
        questionLabel.setAlignment(Align.center);

        answerField = new TextField("", skin);
        answerField.setAlignment(Align.center);
        // Use label & textfield like on main menu for consistency. definitely not
        // avoiding using text button.
        Label instructionLabel = new Label("Press ENTER to Submit", skin);
        instructionLabel.setAlignment(Align.center);

        resultLabel = new Label("", skin);
        resultLabel.setAlignment(Align.center);
        answerField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        answerField.setMaxLength(3);// Best way to deal with integer overflow lol
        table.add(questionLabel).padBottom(20);
        table.row();
        table.add(answerField).width(200).height(40).padBottom(20);
        table.row();
        table.add(instructionLabel).padBottom(20);
        table.row();
        table.add(resultLabel);
    }

    private void checkAnswer() {
        String inputText = answerField.getText();
        // Guaranteed to be an int thanks to filter
        if (inputText.isEmpty()) {
            return;
        }

        int attempt = Integer.parseInt(inputText);
        if (attempt == correctAnswer) {
            resultLabel.setText("Correct!");
            if (onSuccess != null) {
                onSuccess.run();
            }
            game.setScreen(parentScreen);
        } else {
            resultLabel.setText("Incorrect, try again.");
            answerField.setText("");
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            checkAnswer();
        }
    }

    // Must provide implementation for all Screen methods... mostly unused.
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
