package use_case.generate_outfit;

import entity.ClothingItem;

import java.util.List;

public interface ClothingItemSelectionStrategy {
    ClothingItem selectClothingItem(List<ClothingItem> clothingItems);
}
