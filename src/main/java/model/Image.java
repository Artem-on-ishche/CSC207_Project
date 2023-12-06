package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class Image {
    private final File imageFile;
    private byte[] imageData;

    public Image(File imageFile, byte[] imageData) {
        this.imageFile = imageFile;
        this.imageData = imageData;
    }

    public File getImageFile() {
        return imageFile;
    }

    public byte[] getImageData() {
        return imageData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return Arrays.equals(getImageData(), image.getImageData());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getImageData());
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageFile=" + imageFile +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }

    public Image getScaledInstance(int desiredWidth, int desiredHeight, int scaleSmooth) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        BufferedImage originalImage = ImageIO.read(bis);

        var scaledImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, scaleSmooth);

        BufferedImage bufferedScaledImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedScaledImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedScaledImage, "jpg", baos);
        byte[] scaledImageData = baos.toByteArray();

        return new Image(imageFile, scaledImageData);
    }
}
