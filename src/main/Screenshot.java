package main;

import gui.Color;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Kyle on 6/4/2017.
 */
public class Screenshot {
    protected ComplexNumber coordinateOfTopLeftPixel;
    protected int zoomFactor;
    protected int iterations;
    protected BufferedImage theImage;

    public Screenshot(BufferedImage theImage, ComplexNumber coordinateOfTopLeftPixel, int zoomFactor, int iterations) {
        this.theImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                this.theImage.setRGB(column, row, theImage.getRGB(column, row));
            }
        }
        this.coordinateOfTopLeftPixel = new ComplexNumber(coordinateOfTopLeftPixel.getRealComponent(), coordinateOfTopLeftPixel.getImaginaryCoefficient());
        this.zoomFactor = zoomFactor;
        this.iterations = iterations;
    }

    public void drawToFile(int name, long time) {
        try {
            File file = new File("screenshots/" + time + "__" + name + ".png");
            file.createNewFile();
            boolean b = ImageIO.write(theImage, "png", file);

            String screenshotInfoString = "Screenshot " + time + "__" + name + ":\r\n" +
                    "Top Left Coordinates: " + coordinateOfTopLeftPixel.getRealComponent() + " + " + coordinateOfTopLeftPixel.getImaginaryCoefficient() + "i\r\n" +
                    "Zoom (width): 2^" + zoomFactor + "\r\n" +
                    "# of iterations: " + iterations + "\r\n\r\n";

            Files.write(Paths.get("screenshots/screenshotInfo.txt"),
                    screenshotInfoString.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
