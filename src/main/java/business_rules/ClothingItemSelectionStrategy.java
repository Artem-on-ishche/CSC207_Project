package business_rules;

import model.ClothingItem;

import java.util.List;

public interface ClothingItemSelectionStrategy {
    ClothingItem selectClothingItem(List<ClothingItem> clothingItems);
}
