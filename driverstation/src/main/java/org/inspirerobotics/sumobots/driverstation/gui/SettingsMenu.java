package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.inspirerobotics.sumobots.VisibleForTesting;
import org.inspirerobotics.sumobots.driverstation.gui.style.StyleManager;
import org.inspirerobotics.sumobots.driverstation.gui.style.StyleType;

import java.util.Arrays;
import java.util.List;

public class SettingsMenu extends MenuBar {

    @VisibleForTesting
    MenuItem dockWindow;
    @VisibleForTesting
    MenuItem floatWindow;

    public SettingsMenu() {
        this.getMenus().addAll(createMenus());
    }

    private List<Menu> createMenus() {
        Menu styleMenu = new Menu("Styles");
        Menu windowMenu = new Menu("Window");

        addStyleMenuItems(styleMenu);
        addWindowMenuItems(windowMenu);

        return Arrays.asList(styleMenu, windowMenu);
    }

    private void addWindowMenuItems(Menu menu) {
        dockWindow = createMenuItem("Dock Window", event -> {
            StagePositionManager.getInstance().setCurrentMode(StagePositionManager.WindowMode.DOCKED);
        });

        floatWindow = createMenuItem("Float Window", event -> {
            StagePositionManager.getInstance().setCurrentMode(StagePositionManager.WindowMode.FLOAT);
        });

        menu.getItems().addAll(dockWindow, floatWindow);
    }

    private void addStyleMenuItems(Menu menu) {
        for(StyleType type  : StyleType.values()){
            MenuItem item = createMenuItem(type.getDisplayName(), event -> {
                StyleManager.getInstance().styleChildren(type);
            });

            menu.getItems().add(item);
        }
    }

    private MenuItem createMenuItem(String name, EventHandler handler){
        MenuItem item = new MenuItem(name);

        item.setId(name.replace(" ", ""));
        item.setOnAction(handler);

        return item;
    }
}
