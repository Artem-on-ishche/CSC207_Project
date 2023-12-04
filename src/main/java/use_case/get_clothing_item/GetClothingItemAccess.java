package use_case.get_clothing_item;

import model.ClothingItem;

import java.util.Optional;
public interface GetClothingItemAccess {
    Optional<ClothingItem> getClothingItemById(Long itemId);
}
