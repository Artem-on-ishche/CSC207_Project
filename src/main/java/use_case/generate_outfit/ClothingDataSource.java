package use_case.generate_outfit;

import model.ClothingItem;

import java.util.Collection;

public interface ClothingDataSource {
    Collection<ClothingItem> getAllClothingItemsForUser(String username);
}
