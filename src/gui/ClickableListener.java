package gui;

/**
 * Created by Kyle on 4/30/2016.
 */
public class ClickableListener {
    private MouseListener mouseListener;
    private float x, y, rightX, bottomY;
    private boolean mouseLastPressedWithinBounds = false;
    private boolean mousedOver = false;

    public ClickableListener(Clickable client) {
        mouseListener = new MouseListener() {
            @Override
            public void mousePressed() {
                float[] bounds = client.getBounds();
                x = bounds[0];
                y = bounds[1];
                rightX = x + bounds[2];
                bottomY = y + bounds[3];
                int mouseX = Input.getMouseX(), mouseY = Input.getMouseY();
                if (((x < mouseX) && (mouseX < rightX)) && ((y < mouseY) && (mouseY < bottomY))) {
                    mouseLastPressedWithinBounds = true;
                    client.clicked();
                } else {
                    mouseLastPressedWithinBounds = false;
                }
            }

            @Override
            public void mouseReleased() {
                float[] bounds = client.getBounds();
                x = bounds[0];
                y = bounds[1];
                rightX = x + bounds[2];
                bottomY = y + bounds[3];
                int mouseX = Input.getMouseX(), mouseY = Input.getMouseY();
                if (mouseLastPressedWithinBounds) {
                    if (((x < mouseX) && (mouseX < rightX)) && ((y < mouseY) && (mouseY < bottomY))) {
                        client.released();
                    }
                }
            }

            @Override
            public void mouseMoved() {
                float[] bounds = client.getBounds();
                x = bounds[0];
                y = bounds[1];
                rightX = x + bounds[2];
                bottomY = y + bounds[3];
                int mouseX = Input.getMouseX(), mouseY = Input.getMouseY();
                if (((x < mouseX) && (mouseX < rightX)) && ((y < mouseY) && (mouseY < bottomY))) {
                    if (!mousedOver) {
                        mousedOver = true;
                        client.mousedOver();
                    }
                } else if (mousedOver) {
                    mousedOver = false;
                    client.unMousedOver();
                }
            }
        };
    }

    public void addMouseListener() {
        Input.addMouseListener(mouseListener);
    }

    public void removeMouseListener() {
        Input.removeMouseListener(mouseListener);
    }
}
