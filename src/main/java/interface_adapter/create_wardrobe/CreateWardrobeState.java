package interface_adapter.create_wardrobe;

import lombok.Getter;

import java.awt.*;
import java.util.Optional;

@Getter
public class CreateWardrobeState {
    private String imageSrc;
    private String createError;
    private String name;
    private Optional<String> description;
    private int minimumAppropriateTemperature;

    public CreateWardrobeState() {}

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public void setMinimumAppropriateTemperature(int minimumAppropriateTemperature) {
        this.minimumAppropriateTemperature = minimumAppropriateTemperature;
    }

    public void setCreateError(String createError) {
        this.createError = createError;
    }
}
