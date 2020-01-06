package gui;

import java.awt.Font;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

public class Text implements Style {
    public static final Font DEFAULT_FONT = new Font("Verdana", 0, 18);
    public static final TrueTypeFont DEFAULT_TTFONT = new TrueTypeFont(DEFAULT_FONT, true);;
    private TrueTypeFont ttfont;
    private Font font;
    private String string;
    private Color fontColor;

    public Text(String string, Font font, Color fontColor) {
        this.string = string;
        this.font = font;
        this.ttfont = new TrueTypeFont(font, true);
        this.fontColor = fontColor;
    }

    @Override
    public void draw(Rectangle rectangle) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        ttfont.drawString(rectangle.getAbsoluteX(), rectangle.getAbsoluteY(), string, Util.slickColor(fontColor));
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public float getWidth() {
        return ttfont.getWidth(string);
    }

    public float getHeight() {
        return ttfont.getHeight(string);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public TrueTypeFont getTTFont() {
        return ttfont;
    }

    public void setTTFont(TrueTypeFont ttfont) {
        this.ttfont = ttfont;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public static Font adjustedFont(Font font, int newSize) {
        return new Font(font.getFontName(), font.getStyle(), newSize);
    }
}
