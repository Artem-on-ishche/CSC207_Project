package use_case.generate_outfit;

import entity.ClothingItem;

import java.util.List;
import java.util.Random;

public class RandomClothingItemSelectionStrategy implements ClothingItemSelectionStrategy {
    @Override
    public ClothingItem selectClothingItem(List<ClothingItem> clothingItems) {
        var randomIndex = new Random().nextInt(clothingItems.size());
        return clothingItems.get(randomIndex);
    }
}
