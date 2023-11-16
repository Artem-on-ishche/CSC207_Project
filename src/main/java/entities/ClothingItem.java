package entities;

import java.awt.*;
import java.util.Optional;

public record ClothingItem(
        Long id,
        String name,
        Image image,
        ClothingType clothingType,
        Weather appropriateWeather,
        Optional<String> description
) {
}
