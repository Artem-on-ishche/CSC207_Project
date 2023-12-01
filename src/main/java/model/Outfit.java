package model;

import java.util.Map;
import java.util.Objects;

public final class Outfit {
    private final Map<ClothingType, ClothingItem> clothingItems;
    private final boolean isUmbrellaRequired;

    public Outfit(Map<ClothingType, ClothingItem> clothingItems, boolean isUmbrellaRequired) {
        this.clothingItems = clothingItems;
        this.isUmbrellaRequired = isUmbrellaRequired;
    }

    public Map<ClothingType, ClothingItem> getClothingItems() {
        return clothingItems;
    }

    public boolean isUmbrellaRequired() {
        return isUmbrellaRequired;
    }

    public void addClothingItem(ClothingItem clothingItem) {
        clothingItems.put(clothingItem.clothingType(), clothingItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outfit outfit = (Outfit) o;
        return isUmbrellaRequired == outfit.isUmbrellaRequired && Objects.equals(clothingItems, outfit.clothingItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clothingItems, isUmbrellaRequired);
    }

    @Override
    public String toString() {
        return "Outfit{" +
                "clothingItems=" + clothingItems +
                ", isUmbrellaRequired=" + isUmbrellaRequired +
                '}';
    }
}
