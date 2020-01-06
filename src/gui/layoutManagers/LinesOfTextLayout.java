package gui.layoutManagers;

import gui.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kyle on 5/21/2016.
 */
public class LinesOfTextLayout extends LayoutManager {
    private List<Rectangle> components = new LinkedList<>();
    private float leftBorder, topBorder, gapBetweenLines;
    private float currentY;

    public LinesOfTextLayout(float leftBorder, float topBorder, float gapBetweenLines) {
        this.leftBorder = leftBorder;
        this.topBorder = topBorder;
        this.gapBetweenLines = gapBetweenLines;
        this.currentY = topBorder;
    }

    @Override
    public void add(Rectangle component, LayoutConstraints constraints) {
        if (!(component.getStyle() instanceof Text)) {
            throw new RuntimeException("attempted to add something that isn't text to a TextBoxLayout");
        }
        if (constraints != null) {
            throw new RuntimeException("constraints passed to LinesOfTextLayout.add is not equal to null");
        }
        Text text = (Text) component.getStyle();
        float width = text.getWidth();
        float height = text.getHeight();
        component.setX(leftBorder);
        component.setY(currentY);
        component.setWidth(width);
        component.setHeight(height);
        currentY = currentY + height + gapBetweenLines;
    }

    @Override
    public void remove(Rectangle component) {
        System.out.println("LinesOfTextLayout.remove not yet fully supported");
        components.remove(component);
    }

    @Override
    public void clear() {
        components.clear();
    }

    @Override
    public void clientWidthChanged() {
        System.out.println("gui.layoutManagers.LinesOfTextLayout does not yet support width changes");
    }

    @Override
    public void clientHeightChanged() {
        System.out.println("gui.layoutManagers.LinesOfTextLayout does not yet support height changes");
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

    }

    @Override
    public void deactivateComponents() {

    }
}
