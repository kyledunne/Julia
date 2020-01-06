package gui;

/**
 * Created by Kyle on 4/20/2016.
 */
public class Rectangle {
    private Container parent;
    private Style style;
    private float x, y, width, height;
    private float absoluteX, absoluteY;

    private boolean active = false;

    public Rectangle(Style style) {
        this.style = style;
    }

    public void draw() {
        if (style != null) {
            style.draw(this);
        }
    }

    public void setX(float x) {
        this.x = x;
        this.absoluteX = x + parent.getAbsoluteX();
    }

    public void setY(float y) {
        this.y = y;
        this.absoluteY = y + parent.getAbsoluteY();
    }

    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getAbsoluteX() {
        return absoluteX;
    }

    public void setAbsoluteX(float absoluteX) {
        this.absoluteX = absoluteX;
    }

    public float getAbsoluteY() {
        return absoluteY;
    }

    public void setAbsoluteY(float absoluteY) {
        this.absoluteY = absoluteY;
    }

    public void initializeWindow() {
        parent = GUIMain.WINDOW;
        width = GUIMain.getWindowWidth();
        height = GUIMain.getWindowHeight();
        absoluteX = 0;
        absoluteY = 0;
        setX(0);
        setY(0);
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }
}
