package org.inspirerobotics.sumobots.driverstation.gui.style;

import javafx.scene.Parent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.driverstation.gui.SettingsMenu;

import java.util.ArrayList;
import java.util.List;

public class StyleManager {

    private final Logger logger = LogManager.getLogger(SettingsMenu.class);
    private static final StyleManager instance = new StyleManager();

    private final List<Parent> children;

    public StyleManager() {
        this.children = new ArrayList<>();
    }

    public <N extends Parent & Styleable> void addChild(N node){
        children.add(node);
    }

    public void styleChildren(StyleType type){
        logger.info("Setting style: " + type.getDisplayName());

        for(Parent node : children){
            Styleable styleable = (Styleable) node;

            StylesheetLoader.load(type.getDirName() + "/" + styleable.getStylesheetName(), node);
        }
    }

    public static StyleManager getInstance() {
        return instance;
    }
}
