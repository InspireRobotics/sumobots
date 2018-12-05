package org.inspirerobotics.sumobots;

public enum FmsComponent {

    FIELD_SERVER("field_server"), DRIVER_STATION("driver_station");

    private final String sourceName;

    FmsComponent(String name) {
        this.sourceName = name;
    }

    public String getSourceName() {
        return sourceName;
    }
}
