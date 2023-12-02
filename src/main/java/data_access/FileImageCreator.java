package data_access;

import model.ImageData;
import use_case.create_wardrobe.ImageCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileImageCreator implements ImageCreator {
    @Override
    public ImageData fromImageSrc(String src) {
        var file = new File(src);
        try {
            var fileContent = Files.readAllBytes(file.toPath());
            return new ImageData(file, fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
