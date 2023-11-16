package service;

import entities.ClothingItem;

import java.util.Collection;

public interface ClothingDataSource {
    Collection<ClothingItem> getAllClothingItems();
}
