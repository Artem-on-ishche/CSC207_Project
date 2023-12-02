package model;

import java.util.Objects;
import java.util.Optional;

public final class ClothingItem {
    private Long id;
    private String name;
    private Image image;
    private ClothingType clothingType;
    private int minimumAppropriateTemperature;
    private Optional<String> description;

    public ClothingItem(
            Long id,
            String name,
            Image image,
            ClothingType clothingType,
            int minimumAppropriateTemperature,
            Optional<String> description
    ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.clothingType = clothingType;
        this.minimumAppropriateTemperature = minimumAppropriateTemperature;
        this.description = description;
    }

    public boolean isAppropriateForTemperature(double temperature) {
        return temperature >= minimumAppropriateTemperature;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public ClothingType getClothingType() {
        return clothingType;
    }

    public int getMinimumAppropriateTemperature() {
        return minimumAppropriateTemperature;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setClothingType(ClothingType clothingType) {
        this.clothingType = clothingType;
    }

    public void setMinimumAppropriateTemperature(int minimumAppropriateTemperature) {
        this.minimumAppropriateTemperature = minimumAppropriateTemperature;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClothingItem) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.image, that.image) &&
                Objects.equals(this.clothingType, that.clothingType) &&
                this.minimumAppropriateTemperature == that.minimumAppropriateTemperature &&
                Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, clothingType, minimumAppropriateTemperature, description);
    }

    @Override
    public String toString() {
        return "ClothingItem[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "image=" + image + ", " +
                "clothingType=" + clothingType + ", " +
                "minimumAppropriateTemperature=" + minimumAppropriateTemperature + ", " +
                "description=" + description + ']';
    }

}
