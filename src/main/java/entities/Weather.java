package entities;

public enum Weather {
    FREEZING(-300, -10),
    COLD(-10, 10),
    NORMAL(10, 20),
    WARM(20, 30),
    HOT(30, 500);

    private final int minTemperatureInclusive;
    private final int maxTemperatureExclusive;

    Weather(int minTemperature, int maxTemperature) {
        this.minTemperatureInclusive = minTemperature;
        this.maxTemperatureExclusive = maxTemperature;
    }

    public int getMinTemperatureInclusive() {
        return minTemperatureInclusive;
    }

    public int getMaxTemperatureExclusive() {
        return maxTemperatureExclusive;
    }
}
