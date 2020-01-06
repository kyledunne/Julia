package gui;
import org.lwjgl.opengl.GL11;

public class Color implements Style {
    public static final Color BLACK = new Color(0f, 0f, 0f);
    public static final Color WHITE = new Color(1f, 1f, 1f);
    public static final Color TRANSPARENT_BLACK = new Color(0f, 0f, 0f, 0f);
    public static final Color TRANSPARENT = TRANSPARENT_BLACK;
    public static final Color TRANSPARENT_WHITE = new Color(1f, 1f, 1f, 0f);
    public static final Color RED = new Color(1f, 0f, 0f);
    public static final Color GREEN = new Color(0f, 1f, 0f);
    public static final Color DARK_GREEN = new Color(.0f, .5f, .0f);
    public static final Color BLUE = new Color(0f, 0f, 1f);
    public static final Color LIGHT_PURPLE = new Color(1f, 0f, 1f);
    public static final Color CYAN = new Color(0f, 1f, 1f);
    public static final Color YELLOW = new Color(1f, 1f, 0f);
    public static final Color ORANGE = new Color(1f, 165 / 255f, 0f);
    public static final Color DARK_ORANGE = new Color(1f, 200 / 255f, 35f);
    public static final Color DARK_GREY = new Color(50, 50, 50);
    public static final Color DARK_BLUE = new Color(12, 0, 200);
    public static final Color GRAY = new Color(.7f, .7f, .7f);
    public static final Color BROWN = new Color(139, 90, 0);
    public static final Color CARROT = new Color(237, 145, 33);
    public static final Color GREEN_GRASS = new Color(.3f, .9f, .3f);
    public static final Color GREEN_GRASS_ALT = new Color(.2f, 1f, .2f);

    public float r, g, b, a;

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        clamp();
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public Color(int r, int g, int b, int a) {
        this(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    public Color(int r, int g, int b) {
        this(r / 255f, g / 255f, b / 255f, 1);
    }

    public Color(Color color) {
        this(color.r, color.g, color.b, color.a);
    }

    public Color(double r, double g, double b, double a) {
        this((float)r, (float)g, (float)b, (float)a);
    }

    public Color(double r, double g, double b) {
        this(r, g, b, 1);
    }

    @Override
    public void draw(Rectangle rectangle) {
        if(this.a != 0.0F) {
            glColor4f(this);
            GL11.glBegin(GL11.GL_POLYGON);
            GL11.glVertex2f(rectangle.getAbsoluteX(), rectangle.getAbsoluteY());
            GL11.glVertex2f(rectangle.getAbsoluteX() + rectangle.getWidth(), rectangle.getAbsoluteY());
            GL11.glVertex2f(rectangle.getAbsoluteX() + rectangle.getWidth(), rectangle.getAbsoluteY() + rectangle.getHeight());
            GL11.glVertex2f(rectangle.getAbsoluteX(), rectangle.getAbsoluteY() + rectangle.getHeight());
            GL11.glEnd();
        }
    }

    // A bunch of utility methods

    /** MAKES THE TEXTURE GO ALL THIS COLOR AND OOH ITS COOL */
    public void bind() {
        new org.newdawn.slick.Color(r, g, b).bind();
    }

    public void adjust(int r, int g, int b) {
        this.r += r / 255f;
        this.g += g / 255f;
        this.b += b / 255f;
        clamp();
    }

    public void adjust(int r, int g, int b, int a) {
        this.r += r / 255f;
        this.g += g / 255f;
        this.b += b / 255f;
        this.b += a / 255f;
        clamp();
    }

    public void adjust(int a) {
        this.a += a / 255f;
        clamp();
    }

    public void adjust(float r, float g, float b) {
        this.r += r;
        this.g += g;
        this.b += b;
        clamp();
    }

    public void adjust(float r, float g, float b, float a) {
        this.r += r;
        this.g += g;
        this.b += b;
        this.b += a;
        clamp();
    }

    public void adjust(float a) {
        this.a += a;
        clamp();
    }

    public void invert() {
        this.r = 1 - r;
        this.g = 1 - g;
        this.b = 1 - b;
    }

    private void clamp() {
        if(r > 1)
            r = 1;
        else if(r < 0)
            r = 0;
        if(g > 1)
            g = 1;
        else if(g < 0)
            g = 0;
        if(b > 1)
            b = 1;
        else if(b < 0)
            b = 0;
        if(a > 1)
            a = 1;
        else if(a < 0)
            a = 0;
    }

    public static Color adjustAColorsAlpha(Color color, float newAlpha) {
        return new Color(color.r, color.g, color.b, newAlpha);
    }

    public static Color adjustAColor(Color color, float lightnessAdjustment) {
        return new Color(color.r + lightnessAdjustment, color.g + lightnessAdjustment, color.b + lightnessAdjustment, color.a);
    }

    public static Color adjustAColor(Color color, float rAdjustment, float gAdjustment, float bAdjustment) {
        return new Color(color.r + rAdjustment, color.g + gAdjustment, color.b + bAdjustment, color.a);
    }

    public static Color adjustAColor(Color color, float rAdjustment, float gAdjustment, float bAdjustment, float aAdjustment) {
        return new Color(color.r + rAdjustment, color.g + gAdjustment, color.b + bAdjustment, color.a + aAdjustment);
    }

    public static void glClearColor(Color color) {
        GL11.glClearColor(color.r, color.g, color.b, color.a);
    }

    public static void glColor4f(Color color) {
        GL11.glColor4f(color.r, color.g, color.b, color.a);
    }

    public static Color randomColor() {
        return new Color(Util.RAND.nextInt(), Util.RAND.nextInt(255), Util.RAND.nextInt(255));
    }

    public static Color randomNonOpaqueColor() {
        return new Color(Util.RAND.nextInt(), Util.RAND.nextInt(255), Util.RAND.nextInt(255), Util.RAND.nextInt(255));
    }

    public static Color invertedColor(Color color) {
        return new Color(1 - color.r, 1 - color.g, 1 - color.b, color.a);
    }

    public static boolean isCloserToBlack(Color color) {
        return (color.r + color.g + color.b < 1.5);
    }
}

