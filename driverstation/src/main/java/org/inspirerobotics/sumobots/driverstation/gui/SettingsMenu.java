package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.inspirerobotics.sumobots.driverstation.gui.style.StyleManager;
import org.inspirerobotics.sumobots.driverstation.gui.style.StyleType;

public class SettingsMenu extends MenuBar {

    public SettingsMenu() {
        this.getMenus().add(createStyleMenu());
    }

    private Menu createStyleMenu() {
        Menu menu = new Menu("Styles");

        addStyleMenuItems(menu);

        return menu;
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

        item.setOnAction(handler);

        return item;
    }
}
