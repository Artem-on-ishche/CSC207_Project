package use_case.get_clothing_item;

import model.ClothingItem;

import java.util.Optional;

public record GetClothingItemOutputData(Optional<ClothingItem> clothingItem, boolean useCaseFailed){
}
