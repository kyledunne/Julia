package gui;

import gui.layoutManagers.CoordinatesLayout;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.LWJGLException;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Kyle on 5/19/2016.
 */
public class GUIMain {
    public static LayeredContainer WINDOW;
    public static String[] ARGS;

    public static void main(String[] args) {
        try {
            Display.setFullscreen(true);
            Display.create();
        } catch (LWJGLException e) { e.printStackTrace(); }
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 0, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glTranslatef(.375f, .375f, 0);
        glDisable(GL_DEPTH_TEST);
        Color.glClearColor(new Color(25, 23, 199));
        glClear(GL_COLOR_BUFFER_BIT);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glShadeModel(GL_SMOOTH);
        glDisable(GL_LIGHTING);
        glClearDepth(1);

        ARGS = args;

        init();
        WINDOW.activate();

        glClear(GL_COLOR_BUFFER_BIT);
        render();
        Display.update();

        while(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            glClear(GL_COLOR_BUFFER_BIT);
            checkEvents();
            render();
            Display.sync(main.Main.fps);
            Display.update();
        }

        main.Main.exiting();
        System.out.println("Escape pressed");
        System.exit(0);
    }

    private static void init() {
        WINDOW = new LayeredContainer(Color.TRANSPARENT, new CoordinatesLayout());
        WINDOW.initializeWindow();
        main.Main.init();
    }

    /** SUP */
    private static void checkEvents() {
        main.Main.checkEvents();
    }

    private static void render() {
        main.Main.render();
    }

    public static int getWindowWidth() {
        return Display.getWidth();
    }

    public static int getWindowHeight() {
        return Display.getHeight();
    }
}
