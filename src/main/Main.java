package main;

import gui.*;
import gui.Color;
import gui.Container;
import gui.Image;
import gui.Rectangle;
import gui.layoutManagers.*;
import gui.layoutManagers.GridLayout;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Kyle on 12/17/2016.
 */
public class Main {
    public static final boolean DEMO = false;
    public static final int GRANULAR_NUM_COLORS = 1 + 14 + 1 + 14 + 1 + 14;

    public static final int INITIAL_ITERATIONS = 511;
    public static final int fps = 30;
    public static int ITERATIONS = INITIAL_ITERATIONS;
    public static ComplexNumber JULIA_CONSTANT;
    public static final int MANDELBROT_ITERATIONS_SINGLE_CHECK = 10000;
    public static final int MANDELBROT_ITERATIONS = 511;

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public static final double ASPECT = WIDTH / ((double)HEIGHT);
    public static int currentPowerOfTwo = 2;
    public static double currentHorizontalScale = 4;
    public static double currentVerticalScale = currentHorizontalScale / ASPECT;
    public static ComplexNumber currentCoordinateOfTopLeftPixel = new ComplexNumber(
            -2, currentVerticalScale / 2);

    private static ArrayList<ComplexNumber> topLeftCoordinatesAtEachLevel = new ArrayList<>();

    public static Rectangle background;
    private static BufferedImage backgroundImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    public static MouseListener mouseListener;

    private static ArrayList<Screenshot> screenshots = new ArrayList<>();

    public static Container complexGridOverlayContainer;
    public static Rectangle realAxis;
    public static Rectangle imaginaryAxis;

    public static Rectangle oneLine;
    public static Rectangle negativeOneLine;
    public static Rectangle iLine;
    public static Rectangle negativeILine;

    public static Rectangle one;
    public static Rectangle negativeOne;
    public static Rectangle i;
    public static Rectangle negativeI;

    public static boolean displayingAxes = true;
    public static final boolean AXES_GET_SWITCHED_OFF = false;

    private static int[][] pixelIterationVals = new int[HEIGHT][WIDTH];

    private static Container textOverlayLayer;
    private static Container textGrid;
    private static Rectangle zoomTextBox;
    private static Rectangle iterationsTextBox;
    private static Rectangle juliaConstantTextBox;
    private static Rectangle mandelbrotStatusTextBox;
    private static Rectangle screenshotSavedTextBox;

    private static String juliaConstantString;
    private static String mandelbrotStatusString;
    private static String screenshotSavedString = "Screenshot saved";
    private static boolean displayingScreenshotSavedText = false;

    public static boolean mandelbrotMode = false;

    private enum ColoringMode {
        GRADUAL, GRADUAL_WITH_CUTOFF, GRANULAR
    }

    private static ColoringMode coloringMode = ColoringMode.GRANULAR;

    public static void init() {
        if (GUIMain.ARGS.length != 2) {
            throw new RuntimeException("Invalid Program Arguments");
        }
        JULIA_CONSTANT = new ComplexNumber(Double.parseDouble(GUIMain.ARGS[0]), Double.parseDouble(GUIMain.ARGS[1]));
        juliaConstantString = "Julia Constant: " + JULIA_CONSTANT.getRealComponent() + " + " + JULIA_CONSTANT.getImaginaryCoefficient() + "i";
        int mandelbrotValue = JULIA_CONSTANT.getMandelbrotValueSingleCheck();
        if (mandelbrotValue == MANDELBROT_ITERATIONS_SINGLE_CHECK) {
            mandelbrotStatusString = "Mandelbrot Value = MAX";
        } else {
            mandelbrotStatusString = "Mandelbrot Value = " + mandelbrotValue;
        }

        if (DEMO) {
            ITERATIONS = 1;
        }

        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                ComplexNumber thisPixelsComplexCoordinate = new ComplexNumber(currentCoordinateOfTopLeftPixel.getRealComponent() +
                        (column / ((double)WIDTH)) * currentHorizontalScale,
                        currentCoordinateOfTopLeftPixel.getImaginaryCoefficient() -
                                (row / ((double)HEIGHT)) * currentVerticalScale);
                int juliaValue = thisPixelsComplexCoordinate.getJuliaValue();
                pixelIterationVals[row][column] = juliaValue;
                int r, g, b;
                if (juliaValue == ITERATIONS) {
                    r = 0; g = 0; b = 0;
                } else if (juliaValue == 0) {
                    r = 0; g = 0; b = 255;
                } else {
                    juliaValue = (juliaValue + 3) % GRANULAR_NUM_COLORS;
                    if (juliaValue < 15) {
                        r = 0;
                        g = juliaValue * 17;
                        b = (15 - juliaValue) * 17;
                    } else if (juliaValue < 30) {
                        juliaValue = juliaValue - 15;
                        b = 0;
                        r = juliaValue * 17;
                        g = (15 - juliaValue) * 17;
                    } else {
                        juliaValue = juliaValue - 30;
                        g = 0;
                        b = juliaValue * 17;
                        r = (15 - juliaValue) * 17;
                    }
                }
                r = r << 16;
                g = g << 8;
                int rgb = 0xFF000000 + r + g + b;
                backgroundImage.setRGB(column, row, rgb);
            }
        }

        background = new Rectangle(new Image(backgroundImage, null, null));
        GUIMain.WINDOW.addLayer(background);

        mouseListener = new MouseListener() {
            @Override
            public void mousePressed() {
                if (mandelbrotMode) {
                    mandelbrotMode = false;
                    int mouseX = Input.getMouseX();
                    int mouseY = Input.getMouseY();
                    JULIA_CONSTANT = new ComplexNumber(currentCoordinateOfTopLeftPixel.getRealComponent() +
                            (mouseX / ((double)WIDTH)) * currentHorizontalScale,
                            currentCoordinateOfTopLeftPixel.getImaginaryCoefficient() -
                                    (mouseY / ((double)HEIGHT)) * currentVerticalScale);
                    currentPowerOfTwo = 2;
                    currentHorizontalScale = Math.pow(2, currentPowerOfTwo);
                    currentVerticalScale = currentHorizontalScale / ASPECT;
                    currentCoordinateOfTopLeftPixel = new ComplexNumber(
                            -2, currentVerticalScale / 2);
                    adjustComplexGridOverlay();
                    scaleChanged();
                    ((Text)juliaConstantTextBox.getStyle()).setString("Julia Constant: " + JULIA_CONSTANT.getRealComponent() + " + " + JULIA_CONSTANT.getImaginaryCoefficient() + "i");
                    int mandelbrotValue = JULIA_CONSTANT.getMandelbrotValueSingleCheck();
                    if (mandelbrotValue == MANDELBROT_ITERATIONS_SINGLE_CHECK) {
                        mandelbrotStatusString = "Mandelbrot Value = MAX";
                    } else {
                        mandelbrotStatusString = "Mandelbrot Value = " + mandelbrotValue;
                    }
                    ((Text)mandelbrotStatusTextBox.getStyle()).setString(mandelbrotStatusString);
                    return;
                }
                if (DEMO) {
                    if (ITERATIONS < INITIAL_ITERATIONS) {
                        ITERATIONS++;
                        scaleChanged();
                        ((Text)iterationsTextBox.getStyle()).setString(getIterationsString());
                        return;
                    }
                }
                currentPowerOfTwo--;
                int column = Input.getMouseX();
                int row = Input.getMouseY();
                double realComponentAtMouseLocation = currentCoordinateOfTopLeftPixel.getRealComponent() +
                        (column / ((double)WIDTH)) * currentHorizontalScale;
                double imaginaryCoefficientAtMouseLocation = currentCoordinateOfTopLeftPixel.getImaginaryCoefficient() -
                        (row / ((double)HEIGHT)) * currentVerticalScale;
                currentVerticalScale = currentVerticalScale / 2.0;
                currentHorizontalScale = currentHorizontalScale / 2.0;
                currentCoordinateOfTopLeftPixel.setRealComponent(realComponentAtMouseLocation
                        - (currentHorizontalScale / 2.0));
                currentCoordinateOfTopLeftPixel.setImaginaryCoefficient(imaginaryCoefficientAtMouseLocation
                        + (currentVerticalScale / 2.0));
                topLeftCoordinatesAtEachLevel.add(null);
                topLeftCoordinatesAtEachLevel.set(2 - currentPowerOfTwo, currentCoordinateOfTopLeftPixel.clone());
                scaleChanged();
                if (AXES_GET_SWITCHED_OFF) {
                    if (displayingAxes) {
                        displayingAxes = false;
                        GUIMain.WINDOW.removeLayer(complexGridOverlayContainer);
                    }
                } else {
                    adjustComplexGridOverlay();
                }
            }

            @Override
            public void mouseReleased() {

            }

            @Override
            public void mouseMoved() {

            }
        };
        Input.addMouseListener(mouseListener);

        topLeftCoordinatesAtEachLevel.add(0, currentCoordinateOfTopLeftPixel.clone());

        complexGridOverlayContainer = new Container(null, new CoordinatesLayout());
        GUIMain.WINDOW.addLayer(complexGridOverlayContainer);

        realAxis = new Rectangle(Color.adjustAColorsAlpha(Color.WHITE, .67f));
        imaginaryAxis = new Rectangle(Color.adjustAColorsAlpha(Color.WHITE, .67f));

        complexGridOverlayContainer.addComponent(realAxis, new CoordinatesLayout.Constraints(0, HEIGHT / 2, WIDTH, 1));
        complexGridOverlayContainer.addComponent(imaginaryAxis, new CoordinatesLayout.Constraints(WIDTH / 2, 0, 1, HEIGHT));

        oneLine = new Rectangle(Color.adjustAColorsAlpha(Color.WHITE, .33f));
        negativeOneLine = new Rectangle(Color.adjustAColorsAlpha(Color.WHITE, .33f));
        iLine = new Rectangle(Color.adjustAColorsAlpha(Color.WHITE, .33f));
        negativeILine = new Rectangle(Color.adjustAColorsAlpha(Color.WHITE, .33f));

        complexGridOverlayContainer.addComponent(oneLine, new CoordinatesLayout.Constraints(WIDTH * 3 / 4, 0, 1, HEIGHT));
        complexGridOverlayContainer.addComponent(negativeOneLine, new CoordinatesLayout.Constraints(WIDTH / 4, 0, 1, HEIGHT));
        complexGridOverlayContainer.addComponent(iLine, new CoordinatesLayout.Constraints(0, (HEIGHT / 2) - (WIDTH / 4), WIDTH, 1));
        complexGridOverlayContainer.addComponent(negativeILine, new CoordinatesLayout.Constraints(0, (HEIGHT / 2) + (WIDTH / 4), WIDTH, 1));

        int fontSize = 25;
        int fontOffset = 10;

        Font iFont = new Font("Verdana", Font.ITALIC, fontSize);
        Font numberFont = new Font("Verdana", Font.PLAIN, fontSize);

        one = new Rectangle(new Text("1", numberFont, Color.WHITE));
        negativeOne = new Rectangle(new Text("-1", numberFont, Color.WHITE));
        i = new Rectangle(new Text("i", iFont, Color.WHITE));
        negativeI = new Rectangle(new Text("-i", iFont, Color.WHITE));

        complexGridOverlayContainer.addComponent(one, new CoordinatesLayout.Constraints((WIDTH * 3 / 4) + fontOffset, (HEIGHT / 2) - (fontOffset + fontSize), 20, 18));
        complexGridOverlayContainer.addComponent(negativeOne, new CoordinatesLayout.Constraints((WIDTH / 4) + fontOffset, (HEIGHT / 2) - (fontOffset + fontSize), 20, 18));
        complexGridOverlayContainer.addComponent(i, new CoordinatesLayout.Constraints((WIDTH / 2) + fontOffset, (HEIGHT / 2) - (WIDTH / 4) - (fontOffset + fontSize), 20, 18));
        complexGridOverlayContainer.addComponent(negativeI, new CoordinatesLayout.Constraints((WIDTH / 2) + fontOffset, (HEIGHT / 2) + (WIDTH / 4) - (fontOffset + fontSize), 20, 18));

        textOverlayLayer = new Container(null, new CoordinatesLayout());
        GridLayout textGridLayout = GridLayout.new1(5, 1, 10, 0, 5, 0,0, 10);
        textGrid = new Container(null, textGridLayout);
        textOverlayLayer.addComponent(textGrid, new CoordinatesLayout.Constraints(0, 0, 300, 130));
        GUIMain.WINDOW.addLayer(textOverlayLayer);
        textGridLayout.finalize1();

        zoomTextBox = new Rectangle(new Text(getZoomString(), Text.DEFAULT_FONT, Color.WHITE));
        iterationsTextBox = new Rectangle(new Text(getIterationsString(), Text.DEFAULT_FONT, Color.WHITE));
        screenshotSavedTextBox = new Rectangle(new Text(screenshotSavedString, Text.DEFAULT_FONT, Color.WHITE));
        juliaConstantTextBox = new Rectangle(new Text(juliaConstantString, Text.DEFAULT_FONT, Color.WHITE));
        mandelbrotStatusTextBox = new Rectangle(new Text(mandelbrotStatusString, Text.DEFAULT_FONT, Color.WHITE));
        textGrid.addComponent(zoomTextBox, new GridLayout.Constraints(0,0));
        textGrid.addComponent(iterationsTextBox, new GridLayout.Constraints(1,0));
        textGrid.addComponent(juliaConstantTextBox, new GridLayout.Constraints(2, 0));
        textGrid.addComponent(mandelbrotStatusTextBox, new GridLayout.Constraints(3, 0));
    }

    public static void checkEvents() {
        Input.checkInputs();
        if (Input.isSKeyDown()) {
            if (!Input.wasSKeyDown()) {
                if (mandelbrotMode) {
                    screenshots.add(new Screenshot(backgroundImage, currentCoordinateOfTopLeftPixel, currentPowerOfTwo, ITERATIONS));
                } else {
                    screenshots.add(new JuliaScreenshot(backgroundImage, currentCoordinateOfTopLeftPixel, currentPowerOfTwo, ITERATIONS, JULIA_CONSTANT));
                }
                displayingScreenshotSavedText = true;
                textGrid.addComponent(screenshotSavedTextBox, new GridLayout.Constraints(4,0));
            }
        }
        if (Input.isOKeyDown()) {
            if (!Input.wasOKeyDown()) {
                currentPowerOfTwo++;
                currentCoordinateOfTopLeftPixel = topLeftCoordinatesAtEachLevel.get(2 - currentPowerOfTwo).clone();
                currentHorizontalScale = Math.pow(2, currentPowerOfTwo);
                currentVerticalScale = currentHorizontalScale / ASPECT;
                adjustComplexGridOverlay();
                scaleChanged();
            }
        }
        if (Input.isCKeyDown()) {
            if (!Input.wasCKeyDown()) {
                if (coloringMode == ColoringMode.GRADUAL) {
                    coloringMode = ColoringMode.GRANULAR;
                    setPixelsWithGranularColoring();
                } else if (coloringMode == ColoringMode.GRADUAL_WITH_CUTOFF) {
                    coloringMode = ColoringMode.GRADUAL;
                    setPixelsWithGradualColoring();
                } else if (coloringMode == ColoringMode.GRANULAR) {
                    coloringMode = ColoringMode.GRADUAL_WITH_CUTOFF;
                    setPixelsWithGradualWithCutoffColoring();
                }
                if (displayingScreenshotSavedText) {
                    displayingScreenshotSavedText = false;
                    textGrid.removeComponent(screenshotSavedTextBox);
                }
            }
        }
        if (Input.isAKeyDown()) {
            if (!Input.wasAKeyDown()) {
                if (displayingAxes) {
                    displayingAxes = false;
                    GUIMain.WINDOW.removeLayer(complexGridOverlayContainer);
                } else {
                    displayingAxes = true;
                    GUIMain.WINDOW.addLayer(complexGridOverlayContainer);
                    adjustComplexGridOverlay();
                }
            }
        }
        if (!mandelbrotMode) {
            if (Input.isMKeyDown()) {
                if (!Input.wasMKeyDown()) {
                    mandelbrotMode = true;
                    ITERATIONS = 511;
                    ((Text)iterationsTextBox.getStyle()).setString("Iterations: 511");
                    currentPowerOfTwo = 2;
                    currentHorizontalScale = Math.pow(2, currentPowerOfTwo);
                    currentVerticalScale = currentHorizontalScale / ASPECT;
                    currentCoordinateOfTopLeftPixel = new ComplexNumber(
                            -2, currentVerticalScale / 2);
                    adjustComplexGridOverlay();
                    scaleChanged();
                    ((Text)juliaConstantTextBox.getStyle()).setString("");
                    ((Text)mandelbrotStatusTextBox.getStyle()).setString("");
                }
            }
        }
        if (Input.isRightMouseButtonDown()) {
            if (!Input.wasRightMouseButtonDown()) {
                if (ITERATIONS == INITIAL_ITERATIONS) {
                    ITERATIONS = 10000;
                    scaleChanged();
                } else if (ITERATIONS > INITIAL_ITERATIONS){
                    ITERATIONS = INITIAL_ITERATIONS;
                    scaleChanged();
                } else if (ITERATIONS < 50) {
                    ITERATIONS = 50;
                    scaleChanged();
                } else if (ITERATIONS < INITIAL_ITERATIONS) {
                    ITERATIONS = INITIAL_ITERATIONS;
                    scaleChanged();
                }
            }
            ((Text)iterationsTextBox.getStyle()).setString(getIterationsString());
            if (displayingScreenshotSavedText) {
                displayingScreenshotSavedText = false;
                textGrid.removeComponent(screenshotSavedTextBox);
            }
        }
    }

    public static void render() {
        GUIMain.WINDOW.draw();
    }

    public static void scaleChanged() {
        if (mandelbrotMode) {
            mandelbrotScaleChanged();
            return;
        }
        if (displayingScreenshotSavedText) {
            displayingScreenshotSavedText = false;
            textGrid.removeComponent(screenshotSavedTextBox);
        }
        ((Text)zoomTextBox.getStyle()).setString(getZoomString());
        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                ComplexNumber thisPixelsComplexCoordinate = new ComplexNumber(currentCoordinateOfTopLeftPixel.getRealComponent() +
                        (column / ((double)WIDTH)) * currentHorizontalScale,
                        currentCoordinateOfTopLeftPixel.getImaginaryCoefficient() -
                                (row / ((double)HEIGHT)) * currentVerticalScale);
                int juliaValue = thisPixelsComplexCoordinate.getJuliaValue();
                pixelIterationVals[row][column] = juliaValue;
            }
        }
        switch (coloringMode) {
            case GRADUAL:
                setPixelsWithGradualColoring();
                break;
            case GRADUAL_WITH_CUTOFF:
                setPixelsWithGradualWithCutoffColoring();
                break;
            case GRANULAR:
                setPixelsWithGranularColoring();
                break;
            default:
                throw new RuntimeException("Error: at least one coloring mode is not accounted for");
        }
    }

    public static void mandelbrotScaleChanged() {
        if (displayingScreenshotSavedText) {
            displayingScreenshotSavedText = false;
            textGrid.removeComponent(screenshotSavedTextBox);
        }
        ((Text)zoomTextBox.getStyle()).setString(getZoomString());
        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                ComplexNumber thisPixelsComplexCoordinate = new ComplexNumber(currentCoordinateOfTopLeftPixel.getRealComponent() +
                        (column / ((double)WIDTH)) * currentHorizontalScale,
                        currentCoordinateOfTopLeftPixel.getImaginaryCoefficient() -
                                (row / ((double)HEIGHT)) * currentVerticalScale);
                int juliaValue = thisPixelsComplexCoordinate.getMandelbrotValue();
                pixelIterationVals[row][column] = juliaValue;
            }
        }
        switch (coloringMode) {
            case GRADUAL:
                setPixelsWithGradualColoring();
                break;
            case GRADUAL_WITH_CUTOFF:
                setPixelsWithGradualWithCutoffColoring();
                break;
            case GRANULAR:
                setPixelsWithGranularColoring();
                break;
            default:
                throw new RuntimeException("Error: at least one coloring mode is not accounted for");
        }
    }

    public static void exiting() {
        long time = System.currentTimeMillis();
        int numShots = screenshots.size();
        for (int i = 0; i < numShots; i++) {
            screenshots.get(i).drawToFile(i, time);
        }
    }

    public static void adjustComplexGridOverlay() {
        // TODO
        complexGridOverlayContainer.empty();
        double leftX = currentCoordinateOfTopLeftPixel.getRealComponent();
        double topY = currentCoordinateOfTopLeftPixel.getImaginaryCoefficient();
        double rightX = leftX + currentHorizontalScale;
        double bottomY = topY - currentVerticalScale;
        if (leftX < 0 && rightX > 0) {
            double newX = ((-1 * leftX) / (currentHorizontalScale)) * WIDTH;
            complexGridOverlayContainer.addComponent(imaginaryAxis, new CoordinatesLayout.Constraints((int) newX, 0, 1, HEIGHT));
        }
        if (leftX < -1 && rightX > -1) {
            double newX = ((-1 - leftX) / (currentHorizontalScale)) * WIDTH;
            complexGridOverlayContainer.addComponent(negativeOneLine, new CoordinatesLayout.Constraints((int) newX, 0, 1, HEIGHT));
        }
        if (leftX < 1 && rightX > 1) {
            double newX = ((-1 * (leftX - 1)) / (currentHorizontalScale)) * WIDTH;
            complexGridOverlayContainer.addComponent(oneLine, new CoordinatesLayout.Constraints((int) newX, 0, 1, HEIGHT));
        }
        if (topY > 0 && bottomY < 0) {
            double newY = ((topY) / (currentVerticalScale)) * HEIGHT;
            complexGridOverlayContainer.addComponent(realAxis, new CoordinatesLayout.Constraints(0, (int) newY, WIDTH, 1));
        }
        if (topY > 1 && bottomY < 1) {
            double newY = ((topY - 1) / (currentVerticalScale)) * HEIGHT;
            complexGridOverlayContainer.addComponent(iLine, new CoordinatesLayout.Constraints(0, (int) newY, WIDTH, 1));
        }
        if (topY > -1 && bottomY < -1) {
            double newY = ((topY + 1) / (currentVerticalScale)) * HEIGHT;
            complexGridOverlayContainer.addComponent(negativeILine, new CoordinatesLayout.Constraints(0, (int) newY, WIDTH, 1));
        }
    }

    public static void setCurrentPowerOfTwo(int currentPowerOfTwo) {
        Main.currentPowerOfTwo = currentPowerOfTwo;
    }

    public static int getCurrentPowerOfTwo() {
        return currentPowerOfTwo;
    }

    private static void setPixelsWithGradualColoring() {
        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                int juliaValue = pixelIterationVals[row][column];
                int r, g, b;
                if (juliaValue == ITERATIONS) {
                    r = 0; g = 0; b = 0;
                } else {
                    if (juliaValue < 256) {
                        r = 0; g = juliaValue; b = 255 - juliaValue;
                    } else if (juliaValue < 511) {
                        r = juliaValue - 255; g = 510 - juliaValue; b = 0;
                    } else {
                        juliaValue = juliaValue - 511;
                        juliaValue = juliaValue % 765;
                        if (juliaValue <= 255) {
                            r = 255 - juliaValue; g = 0; b = juliaValue;
                        } else if (juliaValue <= 510) {
                            r = 0; g = juliaValue - 255; b = 510 - juliaValue;
                        } else {
                            r = juliaValue - 510; g = 765 - juliaValue; b = 0;
                        }
                    }
                }
                r = r << 16;
                g = g << 8;
                int rgb = 0xFF000000 + r + g + b;
                backgroundImage.setRGB(column, row, rgb);
            }
        }
        ((Image)background.getStyle()).refreshTexture(backgroundImage);
    }

    private static void setPixelsWithGradualWithCutoffColoring() {
        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                int juliaValue = pixelIterationVals[row][column];
                int r, g, b;
                if (juliaValue == ITERATIONS) {
                    r = 0; g = 0; b = 0;
                } else {
                    if (juliaValue < 256) {
                        r = 0; g = juliaValue; b = 255 - juliaValue;
                    } else if (juliaValue < 511) {
                        r = juliaValue - 255; g = 510 - juliaValue; b = 0;
                    } else {
                        r = 255; g = 0; b = 0;
                    }
                }
                r = r << 16;
                g = g << 8;
                int rgb = 0xFF000000 + r + g + b;
                backgroundImage.setRGB(column, row, rgb);
            }
        }
        ((Image)background.getStyle()).refreshTexture(backgroundImage);
    }

    private static void setPixelsWithGranularColoring() {
        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                int juliaValue = pixelIterationVals[row][column];
                int r, g, b;
                if (juliaValue == ITERATIONS) {
                    r = 0; g = 0; b = 0;
                } else if (juliaValue == 0) {
                    r = 0; g = 0; b = 255;
                } else {
                    juliaValue = (juliaValue + 3) % GRANULAR_NUM_COLORS;
                    if (juliaValue < 15) {
                        r = 0;
                        g = juliaValue * 17;
                        b = (15 - juliaValue) * 17;
                    } else if (juliaValue < 30) {
                        juliaValue = juliaValue - 15;
                        b = 0;
                        r = juliaValue * 17;
                        g = (15 - juliaValue) * 17;
                    } else {
                        juliaValue = juliaValue - 30;
                        g = 0;
                        b = juliaValue * 17;
                        r = (15 - juliaValue) * 17;
                    }
                }
                r = r << 16;
                g = g << 8;
                int rgb = 0xFF000000 + r + g + b;
                backgroundImage.setRGB(column, row, rgb);
            }
        }
        ((Image)background.getStyle()).refreshTexture(backgroundImage);
    }

    private static String getZoomString() {
        return "Zoom (width): 2^" + currentPowerOfTwo;
    }

    private static String getIterationsString() {
        return "Iterations: " + ITERATIONS;
    }
}
