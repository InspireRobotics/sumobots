package org.inspirerobotics.sumobots.driverstation.gui.file;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;

public class GuiFileLoadException extends SumobotsRuntimeException {

    public GuiFileLoadException(String message) {
        super(message);
    }

    public GuiFileLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
