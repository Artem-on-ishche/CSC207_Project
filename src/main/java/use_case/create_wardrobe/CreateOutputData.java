package use_case.create_wardrobe;

import model.ClothingItem;

public record CreateOutputData(ClothingItem newClothingItem, boolean useCaseFailed) {
}
