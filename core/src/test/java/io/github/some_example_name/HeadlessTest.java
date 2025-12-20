package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public abstract class HeadlessTest {

    static {
        HeadlessApplicationConfiguration config =
            new HeadlessApplicationConfiguration();

        new HeadlessApplication(new ApplicationAdapter() {}, config);
    }
}
