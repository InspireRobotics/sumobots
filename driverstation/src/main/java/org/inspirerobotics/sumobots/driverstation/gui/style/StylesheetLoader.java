package org.inspirerobotics.sumobots.driverstation.gui.style;

import javafx.scene.Parent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class StylesheetLoader {

    private static final Logger logger = LogManager.getLogger(StylesheetLoader.class);

    static void load(String path, Parent node){
        path = formatPath(path);
        logger.debug("Loading style sheet: {}", path);
        URL url = StylesheetLoader.class.getClass().getResource(path);

        if (url == null) {
            throw new StylesheetLoadException("Failed to load resource: " + path);
        }

        String css = url.toExternalForm();
        node.getStylesheets().clear();
        node.getStylesheets().add(css);
    }

    private static String formatPath(String name) {
        return "/css/" + name + ".css";
    }

}
