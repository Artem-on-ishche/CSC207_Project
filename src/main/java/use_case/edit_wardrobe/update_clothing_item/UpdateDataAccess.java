package use_case.edit_wardrobe.update_clothing_item;

import entity.ClothingItem;

public interface UpdateDataAccess {
    ClothingItem updateClothingItem(ClothingItem clothingItemToUpdate);
}
