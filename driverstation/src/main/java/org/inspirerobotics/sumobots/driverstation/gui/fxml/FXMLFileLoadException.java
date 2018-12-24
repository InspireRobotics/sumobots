package org.inspirerobotics.sumobots.driverstation.gui.fxml;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;

public class FXMLFileLoadException extends SumobotsRuntimeException {

    public FXMLFileLoadException(String message) {
        super(message);
    }

    public FXMLFileLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
