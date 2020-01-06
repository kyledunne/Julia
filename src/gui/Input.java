package gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import java.util.LinkedList;

/**
 * Created by Kyle on 4/29/2016.
 */
public class Input {
    private static int mouseX, mouseY;
    private static int lastFrameMouseX = 0, lastFrameMouseY = 0;
    private static boolean isLeftMouseButtonDown = false, wasLeftMouseButtonDown;
    private static boolean isRightMouseButtonDown = false, wasRightMouseButtonDown;
    private static boolean isSKeyDown = false, wasSKeyDown;
    private static boolean isOKeyDown = false, wasOKeyDown;
    private static boolean isCKeyDown = false, wasCKeyDown;
    private static boolean isAKeyDown = false, wasAKeyDown;
    private static boolean isMKeyDown = false, wasMKeyDown;

    private static LinkedList<MouseListener> mouseListeners = new LinkedList<>();

    public static void checkInputs() {
        lastFrameMouseX = mouseX;
        lastFrameMouseY = mouseY;
        mouseX = Mouse.getX();
        mouseY = GUIMain.getWindowHeight() - Mouse.getY();
        wasLeftMouseButtonDown = isLeftMouseButtonDown;
        isLeftMouseButtonDown = Mouse.isButtonDown(0);
        wasRightMouseButtonDown = isRightMouseButtonDown;
        isRightMouseButtonDown = Mouse.isButtonDown(1);
        wasSKeyDown = isSKeyDown;
        isSKeyDown = Keyboard.isKeyDown(Keyboard.KEY_S);
        wasOKeyDown = isOKeyDown;
        isOKeyDown = Keyboard.isKeyDown(Keyboard.KEY_O);
        wasCKeyDown = isCKeyDown;
        isCKeyDown = Keyboard.isKeyDown(Keyboard.KEY_C);
        wasAKeyDown = isAKeyDown;
        isAKeyDown = Keyboard.isKeyDown(Keyboard.KEY_A);
        wasMKeyDown = isMKeyDown;
        isMKeyDown = Keyboard.isKeyDown(Keyboard.KEY_M);
        boolean mouseMoved = (lastFrameMouseX != mouseX) || (lastFrameMouseY != mouseY);
        boolean mousePressed = (!wasLeftMouseButtonDown) && isLeftMouseButtonDown;
        boolean mouseReleased = wasLeftMouseButtonDown && (!isLeftMouseButtonDown);
        if (mouseMoved) {
            for (MouseListener listener: mouseListeners) {
                listener.mouseMoved();
            }
        }
        if (mousePressed) {
            for (MouseListener listener: mouseListeners) {
                listener.mousePressed();
            }
        }
        if (mouseReleased) {
            for (MouseListener listener: mouseListeners) {
                listener.mouseReleased();
            }
        }
    }

    public static void addMouseListener(MouseListener listener) {
        mouseListeners.add(listener);
    }

    public static void removeMouseListener(MouseListener listener) {
        mouseListeners.remove(listener);
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static void setMouseX(int mouseX) {
        Input.mouseX = mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static void setMouseY(int mouseY) {
        Input.mouseY = mouseY;
    }

    public static int getLastFrameMouseX() {
        return lastFrameMouseX;
    }

    public static void setLastFrameMouseX(int lastFrameMouseX) {
        Input.lastFrameMouseX = lastFrameMouseX;
    }

    public static int getLastFrameMouseY() {
        return lastFrameMouseY;
    }

    public static void setLastFrameMouseY(int lastFrameMouseY) {
        Input.lastFrameMouseY = lastFrameMouseY;
    }

    public static boolean isLeftMouseButtonDown() {
        return isLeftMouseButtonDown;
    }

    public static void setIsLeftMouseButtonDown(boolean isLeftMouseButtonDown) {
        Input.isLeftMouseButtonDown = isLeftMouseButtonDown;
    }

    public static boolean wasLeftMouseButtonDown() {
        return wasLeftMouseButtonDown;
    }

    public static void setWasLeftMouseButtonDown(boolean wasLeftMouseButtonDown) {
        Input.wasLeftMouseButtonDown = wasLeftMouseButtonDown;
    }

    public static boolean isRightMouseButtonDown() {
        return isRightMouseButtonDown;
    }

    public static boolean wasRightMouseButtonDown() {
        return wasRightMouseButtonDown;
    }

    public static boolean isSKeyDown() {
        return isSKeyDown;
    }

    public static boolean wasSKeyDown() {
        return wasSKeyDown;
    }

    public static boolean isOKeyDown() {
        return isOKeyDown;
    }

    public static boolean wasOKeyDown() {
        return wasOKeyDown;
    }

    public static boolean isCKeyDown() {
        return isCKeyDown;
    }

    public static boolean wasCKeyDown() {
        return wasCKeyDown;
    }

    public static boolean isAKeyDown() {
        return isAKeyDown;
    }

    public static boolean wasAKeyDown() {
        return wasAKeyDown;
    }

    public static boolean isMKeyDown() {
        return isMKeyDown;
    }

    public static boolean wasMKeyDown() {
        return wasMKeyDown;
    }
}
