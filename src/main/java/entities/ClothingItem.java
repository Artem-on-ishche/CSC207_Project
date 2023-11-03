package entities;

import java.awt.*;
import java.util.Optional;

public record ClothingItem(
        Long id,
        String name,
        Image image,
        BodyPart bodyPart,
        Weather appropriateWeather,
        Optional<String> description
) {
}
