package model;

import java.io.File;

public record Image(File imageFile, byte[] imageData) {
}
