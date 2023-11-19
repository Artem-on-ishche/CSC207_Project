package entity;

public enum ClothingType {
    HEAD(5),
    NECK(0),
    INNER_UPPER_BODY(Double.MAX_VALUE),
    MIDDLE_UPPER_BODY(15),
    OUTER_UPPER_BODY(5),
    LOWER_BODY(Double.MAX_VALUE),
    HANDS(0),
    FOOTWEAR(Double.MAX_VALUE);

    public final double maximumAppropriateTemperature;

    ClothingType(double maximumAppropriateTemperature) {
        this.maximumAppropriateTemperature = maximumAppropriateTemperature;
    }
}
