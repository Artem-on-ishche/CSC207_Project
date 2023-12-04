package use_case.delete_clothing_item;

import model.ClothingItem;

public interface DeleteDataAccess {
    void deleteClothingItem(Long clothingItemId);
}
