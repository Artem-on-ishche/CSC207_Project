package use_case.view_all_clothing_items;

import model.ClothingItem;

import java.util.List;

public interface AllItemsOfUserDataAccess {
    List<ClothingItem> getAllClothingItemsByUsername(String username);
}
