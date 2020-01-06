package gui;

/**
 * Created by Kyle on 4/20/2016.
 */
public abstract class LayoutManager {
    private Container client;

    public abstract void add(Rectangle component, LayoutConstraints constraints);
    public abstract void remove(Rectangle component);

    /** Unlike remove(), this method must call deactivate()
     * and setParent(null) on each component being removed **/
    public abstract void clear();
    public abstract void clientWidthChanged();
    public abstract void clientHeightChanged();
    public abstract void clientXChanged();
    public abstract void clientYChanged();
    public abstract void drawComponents();
    public abstract void activateComponents();
    public abstract void deactivateComponents();

    public Container getClient() {
        return client;
    }

    public void setClient(Container client) {
        this.client = client;
    }

    public void finalizePlaceholder() {}
}
