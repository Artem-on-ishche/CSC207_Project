package model;

import java.io.File;
import java.util.Arrays;

public final class Image {
    private final File imageFile;
    private final byte[] imageData;

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
}
