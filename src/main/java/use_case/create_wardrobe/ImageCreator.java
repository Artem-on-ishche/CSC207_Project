package use_case.create_wardrobe;

import model.Image;

public interface ImageCreator {
    Image fromImageSrc(String src);
}
