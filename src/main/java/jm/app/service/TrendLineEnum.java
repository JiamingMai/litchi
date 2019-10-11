package jm.app.service;

public enum TrendLineEnum {

    AUTO("auto", 0),
    LINEAR("linear", 1),
    LOGARITHM("logarithm", 2),
    EXPONENTIAL("exponential", 3),
    POLYNOMIAL("polynomial", 4),
    POWER("power", 5),
    MOVING_AVERAGE("movingAverage", 6);

    private String name;
    private int code;

    TrendLineEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static TrendLineEnum getTrendLineTypeByCode(int code) {
        for (TrendLineEnum trendLine : TrendLineEnum.values()) {
            if (trendLine.code == code) {
                return trendLine;
            }
        }
        return null;
    }

    public static TrendLineEnum getTrendLineTypeByName(String name) {
        for (TrendLineEnum trendLine : TrendLineEnum.values()) {
            if (trendLine.equals(name)) {
                return trendLine;
            }
        }
        return null;
    }

}
