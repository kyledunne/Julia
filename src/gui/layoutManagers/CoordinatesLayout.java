package gui.layoutManagers;

import gui.LayoutConstraints;
import gui.LayoutManager;
import gui.Rectangle;

import java.util.LinkedList;

/**
 * Created by Kyle on 5/22/2016.
 */
public class CoordinatesLayout extends LayoutManager {
    private LinkedList<Rectangle> components = new LinkedList<>();

    @Override
    public void add(Rectangle component, LayoutConstraints constraints) {
        components.add(component);
        Constraints c = (Constraints) constraints;
        component.setX(c.getX());
        component.setY(c.getY());
        component.setWidth(c.getWidth());
        component.setHeight(c.getHeight());
    }

    @Override
    public void remove(Rectangle component) {
        components.remove(component);
    }

    @Override
    public void clear() {
        for (Rectangle component: components) {
            component.deactivate();
            component.setParent(null);
        }
        components.clear();
    }

    @Override
    public void clientWidthChanged() {
        //do nothing
    }

    @Override
    public void clientHeightChanged() {
        //do nothing
    }

    @Override
    public void clientXChanged() {
        for (Rectangle component: components) {
            component.setAbsoluteX(component.getX() + getClient().getAbsoluteX());
        }
    }

    @Override
    public void clientYChanged() {
        for (Rectangle component: components) {
            component.setAbsoluteY(component.getY() + getClient().getAbsoluteY());
        }
    }

    @Override
    public void drawComponents() {
        for (Rectangle component: components) {
            component.draw();
        }
    }

    @Override
    public void activateComponents() {
        for (Rectangle component: components) {
            component.activate();
        }
    }

    @Override
    public void deactivateComponents() {
        for (Rectangle component: components) {
            component.deactivate();
        }
    }

    public static class Constraints implements LayoutConstraints {
        private float x, y, width, height;

        public Constraints(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
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

        public float getHeight() {
            return height;
        }
    }
}
