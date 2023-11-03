package service;

import entities.ClothingItem;

import java.util.Collection;
import java.util.Optional;

public interface ClothingDataSource {
    Collection<ClothingItem> getUpperBodyItems(Optional<String> filterBy);
}
