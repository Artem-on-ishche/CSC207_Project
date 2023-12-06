package model;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClothingItem that = (ClothingItem) o;

        if (getMinimumAppropriateTemperature() != that.getMinimumAppropriateTemperature()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (!getName().equals(that.getName())) return false;
        if (getImage() != null ? !getImage().equals(that.getImage()) : that.getImage() != null) return false;
        if (getClothingType() != that.getClothingType()) return false;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + getClothingType().hashCode();
        result = 31 * result + getMinimumAppropriateTemperature();
        result = 31 * result + getDescription().hashCode();
        return result;
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
