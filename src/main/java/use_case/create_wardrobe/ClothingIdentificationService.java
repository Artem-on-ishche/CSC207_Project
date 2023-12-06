package use_case.create_wardrobe;

import model.ClothingType;

import java.io.IOException;

public interface ClothingIdentificationService {
    ClothingType identifyClothingItem(String imageSrc) throws IOException;
}
