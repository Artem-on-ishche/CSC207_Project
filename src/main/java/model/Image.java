package entity;

import java.io.File;

public record Image(File imageFile, byte[] imageData) {
}
