package entity;

public enum TemperatureRange {
    FREEZING(TemperatureBoundaries.MIN_POSSIBLE_TEMPERATURE, -10),
    COLD(-10, 10),
    NORMAL(10, 20),
    WARM(20, 30),
    HOT(30, TemperatureBoundaries.MAX_POSSIBLE_TEMPERATURE),
    ALL_POSSIBLE_TEMPERATURES(TemperatureBoundaries.MIN_POSSIBLE_TEMPERATURE, TemperatureBoundaries.MAX_POSSIBLE_TEMPERATURE);


    private final int minTemperatureInclusive;
    private final int maxTemperatureExclusive;

    TemperatureRange(int minTemperature, int maxTemperature) {
        this.minTemperatureInclusive = minTemperature;
        this.maxTemperatureExclusive = maxTemperature;
    }

    public int getMinTemperatureInclusive() {
        return minTemperatureInclusive;
    }

    public int getMaxTemperatureExclusive() {
        return maxTemperatureExclusive;
    }

    public boolean isTemperatureInRange(double temperature) {
        return temperature >= minTemperatureInclusive && temperature < maxTemperatureExclusive;
    }
}
