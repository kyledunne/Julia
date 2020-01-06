package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JuliaScreenshot extends Screenshot {
    private ComplexNumber juliaConstant;

    public JuliaScreenshot(BufferedImage theImage, ComplexNumber coordinateOfTopLeftPixel, int zoomFactor, int iterations,
                           ComplexNumber juliaConstant) {
        super(theImage, coordinateOfTopLeftPixel, zoomFactor, iterations);
        this.juliaConstant = new ComplexNumber(juliaConstant.getRealComponent(), juliaConstant.getImaginaryCoefficient());
    }

    @Override
    public void drawToFile(int name, long time) {
        try {
            File file = new File("screenshots/" + time + "__" + name + ".png");
            file.createNewFile();
            boolean b = ImageIO.write(theImage, "png", file);

            String screenshotInfoString = "Screenshot " + time + "__" + name + ":\r\n" +
                    "Julia Constant = " + juliaConstant.getRealComponent() + " + " + juliaConstant.getImaginaryCoefficient() + "i\r\n" +
                    "Top Left Coordinates: " + coordinateOfTopLeftPixel.getRealComponent() + " + " + coordinateOfTopLeftPixel.getImaginaryCoefficient() + "i\r\n" +
                    "Zoom (width): 2^" + zoomFactor + "\r\n" +
                    "# of iterations: " + iterations + "\r\n\r\n";

            Files.write(Paths.get("screenshots/screenshotInfo.txt"),
                    screenshotInfoString.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
