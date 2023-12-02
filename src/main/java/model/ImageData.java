package model;

import java.io.File;

public record ImageData(File imageFile, byte[] imageData) {
}
