package model;

import java.awt.*;
import java.util.Optional;

public record ClothingItem(
        Long id,
        String name,
        Image image,
        ClothingType clothingType,
        double minimumAppropriateTemperature,
        Optional<String> description
) {
    public boolean isAppropriateForTemperature(double temperature) {
        return temperature >= minimumAppropriateTemperature;
    }
}
