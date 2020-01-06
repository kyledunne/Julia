package gui.layoutManagers;

import gui.*;
import java.util.List;

/**
 * Created by Kyle on 5/21/2016.
 */
public class TopDownFlowLayout extends LayoutManager {
    private List<Rectangle> components;
    private float leftBorder, rightBorder, topBorder;
    private float verticalGap;
    private float currentY;

    public TopDownFlowLayout(float leftBorder, float rightBorder, float topBorder, float verticalGap) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.topBorder = topBorder;
        this.verticalGap = verticalGap;
        currentY = topBorder;
    }

    @Override
    public void add(Rectangle component, LayoutConstraints constraints) {
        components.add(component);
        component.setX(leftBorder);
        component.setY(currentY);
        component.setWidth(rightBorder - leftBorder);
        component.setHeight(((Constraints) constraints).getHeight());
    }

    @Override
    public void remove(Rectangle component) {
        System.out.println("Removing rectangles from a TopDownFlowLayout is not yet fully supported\n"
                + "(rectangle will be removed but any rectangles below it will not be properly moved)");
//        int index = components.indexOf(component);
        components.remove(component);
//        for (int i = index; i < components.size(); index++) {
//
//        }
    }

    @Override
    public void clear() {
        components.clear();
    }

    @Override
    public void clientWidthChanged() {
        System.out.println("gui.layoutManagers.TopDownFlowLayout does not yet support width changes");
    }

    @Override
    public void clientHeightChanged() {
        System.out.println("gui.layoutManagers.FlowLayout does not yet support height changes");
    }

    @Override
    public void clientXChanged() {
        for (Rectangle component: components) {
            component.setAbsoluteX(component.getX() + getClient().getAbsoluteX());
        }
    }

    @Override
    public void clientYChanged() {
        for (Rectangle component : components) {
            component.setAbsoluteY(component.getY() + getClient().getAbsoluteY());
        }
    }

    @Override
    public void drawComponents() {

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
        private float height;

        public Constraints(float height) {
            this.height = height;
        }

        public float getHeight() {
            return height;
        }
    }
}
