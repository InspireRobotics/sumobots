package org.inspirerobotics.sumobots.driverstation.joystick;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

public class JoystickLibraryLoader {

    private static final Logger logger = LogManager.getLogger(JoystickLibraryLoader.class);

    public static boolean libraryInstalled() {
        try {
            if ("x86".equals(System.getProperty("os.arch"))) {
                System.loadLibrary("jinput-dx8");
            } else {
                System.loadLibrary("jinput-dx8_64");
            }
        } catch (UnsatisfiedLinkError e) {
            return false;
        }

        return true;
    }

    public static void checkLibrary() {
        if (!libraryInstalled()) {
            throw new SumobotsRuntimeException("\n\n\nFailed to load JInput Natives!\n" +
                    "DOWNLOAD NATIVES HERE: https://repo1.maven.org/maven2/net/java/jinput/jinput-platform/2.0.7/\n\n");
        }


    }
}
