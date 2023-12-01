package use_case.create_wardrobe;

import entity.ClothingItem;

import java.awt.*;
import java.util.Optional;

public record CreateInputData(String name,
                              Image image,
                              Optional<String> description,
                              double minimumAppropriateTemperature) {
    public ClothingItem toClothingItem() {
        return new ClothingItem(null, name, image, null, minimumAppropriateTemperature, description);
    }
}
