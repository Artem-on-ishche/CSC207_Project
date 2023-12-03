package use_case.create_wardrobe;

import model.ClothingType;

public interface ClothingIdentificationService {
    ClothingType identifyClothingItem(String imageSrc);
}
