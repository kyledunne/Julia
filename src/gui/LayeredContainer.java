package gui;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Kyle on 4/20/2016.
 */
public class LayeredContainer extends Container {
    private Collection<Rectangle> layers;

    public LayeredContainer(Style style, LayoutManager layoutManager) {
        super(style, layoutManager);
        layers = new LinkedList<>();
    }

    @Override
    public void draw() {
        super.draw();
        for (Rectangle layer: layers) {
            layer.draw();
        }
    }

    public void addLayer(Rectangle layer) {
        layer.setParent(this);
        layers.add(layer);
        layer.setX(getX());
        layer.setY(getY());
        layer.setWidth(getWidth());
        layer.setHeight(getHeight());
    }

    public void removeLayer(Rectangle layer) {
        layers.remove(layer);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        for (Rectangle layer: layers) {
            layer.setWidth(width);
        }
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        for (Rectangle layer: layers) {
            layer.setHeight(height);
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        for (Rectangle layer: layers) {
            layer.setAbsoluteX(getAbsoluteX());
        }
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        for (Rectangle layer: layers) {
            layer.setAbsoluteY(getAbsoluteY());
        }
    }

    @Override
    public void setAbsoluteX(float absoluteX) {
        super.setAbsoluteX(absoluteX);
        for (Rectangle layer: layers) {
            layer.setAbsoluteX(getAbsoluteX());
        }
    }

    @Override
    public void setAbsoluteY(float absoluteY) {
        super.setAbsoluteY(absoluteY);
        for (Rectangle layer: layers) {
            layer.setAbsoluteY(getAbsoluteY());
        }
    }

    @Override
    public void empty() {
        super.empty();
        for (Rectangle layer: layers) {
            if (layer instanceof Container) {
                ((Container) layer).empty();
            }
        }
        layers.clear();
    }
}
