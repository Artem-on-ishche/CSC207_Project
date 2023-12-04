package use_case.get_item_by_id;

import model.ClothingItem;

public interface GetItemByIdDataAccess {
    ClothingItem getClothingItemById(Long itemId);
}
