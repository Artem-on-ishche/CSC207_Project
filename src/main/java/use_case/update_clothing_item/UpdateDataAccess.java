package use_case.update_clothing_item;

import model.ClothingItem;

public interface UpdateDataAccess {
    ClothingItem updateClothingItem(ClothingItem clothingItemToUpdate);
}
