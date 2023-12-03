package use_case.create_wardrobe;

import java.util.Optional;

public record CreateInputData(String name,
                              String imageSrc,
                              Optional<String> description,
                              int minimumAppropriateTemperature) {
}
