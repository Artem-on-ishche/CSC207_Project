package use_case.update_clothing_item;

import model.ClothingItem;

public record UpdateOutputData(ClothingItem updatedClothingItem, boolean useCaseFailed) {
}
