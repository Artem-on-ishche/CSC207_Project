package entity;

import java.util.List;

public interface ClothingItemSelectionStrategy {
    ClothingItem selectClothingItem(List<ClothingItem> clothingItems);
}
