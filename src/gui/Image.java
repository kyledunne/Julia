package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;
import static org.lwjgl.opengl.GL11.*;

public class Image implements Style {
    private Texture texture;
    private Color backgroundColor;
    private Color bindingColor;

    public Image(String texturePath, Color backgroundColor, Color bindingColor) {
        try {
            this.texture = TextureLoader.getTexture(texturePath.substring(texturePath.length() - 3), ResourceLoader.getResourceAsStream(texturePath));
        } catch (IOException e) { e.printStackTrace(); }
        this.backgroundColor = backgroundColor;
        if (bindingColor != null) {
            this.bindingColor = bindingColor;
        } else {
            this.bindingColor = Color.WHITE;
        }

    }

    public Image(BufferedImage bufferedImage, Color backgroundColor, Color bindingColor) {
        try {
            this.texture = BufferedImageUtil.getTexture("", bufferedImage, SGL.GL_NEAREST);
        } catch (IOException e) { e.printStackTrace(); }
        this.backgroundColor = backgroundColor;
        if (bindingColor != null) {
            this.bindingColor = bindingColor;
        } else {
            this.bindingColor = Color.WHITE;
        }
    }

    public void draw(Rectangle rectangle) {
        if(hasBackgroundColor()) {
            backgroundColor.draw(rectangle);
        }
        float textureWidth = texture.getWidth();
        float textureHeight = texture.getHeight();
        glEnable(GL_TEXTURE_2D);
        if (bindingColor != null) {
            bindingColor.bind();
        }
        texture.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(0, 0);
        glVertex2f(rectangle.getAbsoluteX(), rectangle.getAbsoluteY());
        glTexCoord2f(0, textureHeight);
        glVertex2f(rectangle.getAbsoluteX(), rectangle.getAbsoluteY() + rectangle.getHeight());
        glTexCoord2f(textureWidth, textureHeight);
        glVertex2f(rectangle.getAbsoluteX() + rectangle.getWidth(), rectangle.getAbsoluteY() + rectangle.getHeight());
        glTexCoord2f(textureWidth, 0);
        glVertex2f(rectangle.getAbsoluteX() + rectangle.getWidth(), rectangle.getAbsoluteY());
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }

    public void refreshTexture(BufferedImage bufferedImage) {
        try {
            this.texture = BufferedImageUtil.getTexture("", bufferedImage, SGL.GL_NEAREST);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public boolean hasBackgroundColor() {
        return backgroundColor != null;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getBindingColor() {
        return bindingColor;
    }

    public void setBindingColor(Color bindingColor) {
        this.bindingColor = bindingColor;
    }

    public Texture getTexture() {
        return texture;
    }
}
