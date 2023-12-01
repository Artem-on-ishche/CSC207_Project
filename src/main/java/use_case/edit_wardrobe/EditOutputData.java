package use_case.edit_wardrobe;

import model.ClothingItem;

public record EditOutputData(ClothingItem editClothingItem, boolean useCaseFailed) {
}
