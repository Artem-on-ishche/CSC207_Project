package use_case.create_wardrobe;

import model.ImageData;

public interface ImageCreator {
    ImageData fromImageSrc(String src);
}
