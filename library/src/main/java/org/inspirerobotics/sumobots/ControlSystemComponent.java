package org.inspirerobotics.sumobots;

public enum ControlSystemComponent {

    FIELD_SERVER("field_server"), DRIVER_STATION("driver_station");

    private final String sourceName;

    ControlSystemComponent(String name) {
        this.sourceName = name;
    }

    public String getSourceName() {
        return sourceName;
    }
}
