package interface_adapter.create_wardrobe;

import java.awt.*;
import java.util.Optional;

public class CreateWardrobeState {

    private String createError;
    private Image photo;
    private String name;
    private Optional<String> description;
    private double minimumAppropriateTemperature;

    public CreateWardrobeState(CreateWardrobeState copy) {
        photo = copy.photo;
        name = copy.name;
        description = copy.description;
        minimumAppropriateTemperature = copy.minimumAppropriateTemperature;
        createError = copy.createError;
    }

    public CreateWardrobeState() {}

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
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

    public double getMinimumAppropriateTemperature() {
        return minimumAppropriateTemperature;
    }

    public void setMinimumAppropriateTemperature(double minimumAppropriateTemperature) {
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
