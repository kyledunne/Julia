package gui;

/**
 * Created by Kyle on 4/30/2016.
 */
public abstract class ClickableRectangle extends Rectangle implements Clickable {
    ClickableListener listener;

    public ClickableRectangle(Style style) {
        super(style);
        listener = new ClickableListener(this);
    }

    @Override
    public float[] getBounds() {
        return getBounds(this);
    }

    @Override
    public ClickableListener getListener() {
        return listener;
    }

    public static float[] getBounds(Rectangle rectangle) {
        float[] bounds = new float[4];
        bounds[0] = rectangle.getAbsoluteX();
        bounds[1] = rectangle.getAbsoluteY();
        bounds[2] = rectangle.getWidth();
        bounds[3] = rectangle.getHeight();
        return bounds;
    }

    @Override
    public void activate() {
        super.activate();
        listener.addMouseListener();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        listener.removeMouseListener();
    }
}
