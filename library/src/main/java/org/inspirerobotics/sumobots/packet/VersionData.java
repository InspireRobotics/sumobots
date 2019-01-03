package org.inspirerobotics.sumobots.packet;

import org.inspirerobotics.sumobots.Version;

public class VersionData {

    public String version;

    public VersionData() {
        this.version = Version.VERSION;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
