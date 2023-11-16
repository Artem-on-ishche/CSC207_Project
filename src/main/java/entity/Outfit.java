package entity;

import java.util.Map;
import java.util.Objects;

public final class Outfit {
    private final Map<ClothingType, ClothingItem> clothingItems;

    public Outfit(Map<ClothingType, ClothingItem> clothingItems) {
        this.clothingItems = clothingItems;
    }

    public Map<ClothingType, ClothingItem> getClothingItems() {
        return clothingItems;
    }

    public void addClothingItem(ClothingItem clothingItem) {
        clothingItems.put(clothingItem.clothingType(), clothingItem);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Outfit) obj;
        return Objects.equals(this.clothingItems, that.clothingItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clothingItems);
    }

    @Override
    public String toString() {
        return "Outfit[" +
                "clothingItems=" + clothingItems + ']';
    }

}
