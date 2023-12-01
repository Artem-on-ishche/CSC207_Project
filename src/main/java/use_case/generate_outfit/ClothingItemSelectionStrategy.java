package use_case.generate_outfit;

import model.ClothingItem;

import java.util.List;

public interface ClothingItemSelectionStrategy {
    ClothingItem selectClothingItem(List<ClothingItem> clothingItems);
}
