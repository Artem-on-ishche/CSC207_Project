package data_access.persistence;

import model.Image;
import use_case.create_wardrobe.ImageCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileImageCreator implements ImageCreator {
    @Override
    public Image fromImageSrc(String src) {
        var file = new File(src);
        try {
            var fileContent = Files.readAllBytes(file.toPath());
            return new Image(file, fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image convertByteArrayAndSaveToFile(byte[] imageData, String filename) {
        saveToFile(imageData, filename);
        return new Image(new File(filename), imageData);
    }

    public static void saveToFile(byte[] imageData, String filename) {
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            outputStream.write(imageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
