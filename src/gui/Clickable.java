package gui;

/**
 * Created by Kyle on 4/30/2016.
 */
public interface Clickable {
    void mousedOver();
    void unMousedOver();
    void clicked();
    void released();
    float[] getBounds();
    ClickableListener getListener();
}
