package org.inspirerobotics.sumobots.driverstation.gui.style;

public enum StyleType {

    LIGHT("light", "Light"), DARK("dark", "Dark");

    String dirName;

    String displayName;

    StyleType(String dirName, String displayName) {
        this.dirName = dirName;
        this.displayName = displayName;
    }

    public String getDirName() {
        return dirName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
