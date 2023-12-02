package use_case.create_wardrobe;

import model.ClothingItem;

public record CreateOutputData(ClothingItem editClothingItem, boolean useCaseFailed) {
}
