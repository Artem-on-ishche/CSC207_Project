package use_case.create_wardrobe;

import model.ClothingItem;

public interface CreateDataAccess {
    Long addClothingItem(ClothingItem clothingItem, String username);
}
