package use_case.generate_outfit;

import entity.ClothingItem;

import java.util.Collection;

public interface ClothingDataSource {
    Collection<ClothingItem> getAllClothingItems();
}
