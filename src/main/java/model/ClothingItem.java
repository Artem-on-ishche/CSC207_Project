package model;

import java.util.Optional;

public record ClothingItem(
        Long id,
        String name,
        ImageData imageData,
        ClothingType clothingType,
        int minimumAppropriateTemperature,
        Optional<String> description
) {
    public boolean isAppropriateForTemperature(double temperature) {
        return temperature >= minimumAppropriateTemperature;
    }
}
