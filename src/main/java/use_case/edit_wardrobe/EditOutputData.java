package use_case.edit_wardrobe;

import entity.ClothingItem;

public record EditOutputData(ClothingItem editClothingItem, boolean useCaseFailed) {
}
