package org.inspirerobotics.sumobots.driverstation.gui.style;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;

public class StylesheetLoadException extends SumobotsRuntimeException {

    public StylesheetLoadException(String message) {
        super(message);
    }

    public StylesheetLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
