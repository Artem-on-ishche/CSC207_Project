package interface_adapter.create_wardrobe;

import java.awt.*;
import java.util.Optional;

public class CreateWardrobeState {
    private String imageSrc;
    private String createError;
    private String name;
    private Optional<String> description;
    private int minimumAppropriateTemperature;

    public CreateWardrobeState(CreateWardrobeState copy) {
        imageSrc = copy.imageSrc;
        name = copy.name;
        description = copy.description;
        minimumAppropriateTemperature = copy.minimumAppropriateTemperature;
        createError = copy.createError;
    }

    public CreateWardrobeState() {}

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public int getMinimumAppropriateTemperature() {
        return minimumAppropriateTemperature;
    }

    public void setMinimumAppropriateTemperature(int minimumAppropriateTemperature) {
        this.minimumAppropriateTemperature = minimumAppropriateTemperature;
    }

    public String getCreateError() {
        return createError;
    }

    public void setCreateError(String createError) {
        this.createError = createError;
    }

    @Override
    public String toString() {
        return "CreateWardrobeState{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
