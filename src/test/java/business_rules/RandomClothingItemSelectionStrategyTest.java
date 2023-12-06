package business_rules;

import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RandomClothingItemSelectionStrategyTest {

    @Test
    void selectClothingItem() {
        var clothingItems = List.of(
                new ClothingItem(1L, "name1", null, ClothingType.HEAD, 0, Optional.empty()),
                new ClothingItem(2L, "name2", null, ClothingType.HEAD, 0, Optional.empty()),
                new ClothingItem(3L, "name3", null, ClothingType.HEAD, 0, Optional.empty())
        );

        var selectedItem = new RandomClothingItemSelectionStrategy().selectClothingItem(clothingItems);

        assertTrue(clothingItems.contains(selectedItem));
    }
}