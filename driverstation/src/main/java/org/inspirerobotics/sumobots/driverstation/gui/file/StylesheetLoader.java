package org.inspirerobotics.sumobots.driverstation.gui.file;

import javafx.scene.Parent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class StylesheetLoader {

    public static final String DEFAULT_STYLE = "light";

    private static final Logger logger = LogManager.getLogger(StylesheetLoader.class);

    public static void load(String name, Parent node){
        String path = formatPath(name);
        logger.debug("Loading style sheet: {}", path);
        URL url = StylesheetLoader.class.getClass().getResource(path);

        if (url == null) {
            throw new GuiFileLoadException("Failed to load resource: " + path);
        }

        String css = url.toExternalForm();
        node.getStylesheets().add(css);
    }

    private static String formatPath(String name) {
        return "/css/" + DEFAULT_STYLE + "/" + name + ".css";
    }

}
