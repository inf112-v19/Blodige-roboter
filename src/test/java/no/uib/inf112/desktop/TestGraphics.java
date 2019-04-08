package no.uib.inf112.desktop;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import no.uib.inf112.core.GameGraphics;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TestGraphics {
    // This is our "test" application
    private static Application application;
    public static final String TEST_MAP_FOLDER = "testmaps";

    // Before running any tests, initialize the application with the headless backend
    @BeforeClass
    public static void init() {
        GameGraphics.HEADLESS = true;
        application = new HeadlessApplication(mock(ApplicationListener.class));
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // Use Mockito to mock the OpenGL methods since we are running headlessly
        Gdx.gl20 = mock(GL20.class);
        Gdx.gl = Gdx.gl20;

        //run postRunnable at once
        Gdx.app = mock(Application.class);
        doAnswer(invocation -> {
            invocation.getArgumentAt(0, Runnable.class).run();
            return null;
        }).when(Gdx.app).postRunnable(any(Runnable.class));


        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(1);
        when(Gdx.graphics.getHeight()).thenReturn(1);
    }

    // After we are done, clean up the application
    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }
}
