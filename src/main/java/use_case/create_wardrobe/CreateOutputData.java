package use_case.create_wardrobe;

import entity.ClothingItem;

public record CreateOutputData(ClothingItem editClothingItem, boolean useCaseFailed) {
}
