package gui;

/**
 * Created by Kyle on 4/20/2016.
 */
public class Container extends Rectangle {
    private LayoutManager layoutManager;

    public Container(Style style, LayoutManager layoutManager) {
        super(style);
        this.layoutManager = layoutManager;

        layoutManager.setClient(this);
    }

    @Override
    public void draw() {
        super.draw();
        layoutManager.drawComponents();
    }

    public void addComponent(Rectangle component, LayoutConstraints constraints) {
        component.setParent(this);
        if (isActive()) {
            component.activate();
        }
        layoutManager.add(component, constraints);
    }

    public void removeComponent(Rectangle component) {
        component.deactivate();
        component.setParent(null);
        layoutManager.remove(component);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        layoutManager.clientWidthChanged();
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        layoutManager.clientHeightChanged();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        layoutManager.clientXChanged();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        layoutManager.clientYChanged();
    }

    @Override
    public void setAbsoluteX(float absoluteX) {
        super.setAbsoluteX(absoluteX);
        layoutManager.clientXChanged();
    }

    @Override
    public void setAbsoluteY(float absoluteY) {
        super.setAbsoluteY(absoluteY);
        layoutManager.clientYChanged();
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void empty() {
        layoutManager.clear();
    }

    @Override
    public void activate() {
        super.activate();
        layoutManager.activateComponents();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        layoutManager.deactivateComponents();
    }
}
