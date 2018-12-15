package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

public class GuiUtils {

    public static final String CSS_TEXT_FIELD_FILL = "-fx-text-fill";
    public static final String CSS_BACKGROUND = "-fx-background-color";

    public static void styleTextNode(Node node, String foreground, String background){
        style(node, CSS_BACKGROUND, background, CSS_TEXT_FIELD_FILL, foreground);
    }

    public static void style(Node node, String... values){
        if(values.length % 2 != 0){
            throw new IllegalArgumentException("Values must be multiple of two! Found: " + Arrays.toString(values));
        }

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < values.length; i += 2){
            builder.append(values[i] + ":" + values[i + 1] + ";");
        }

        node.setStyle(builder.toString());
    }

    public static void setStyleClass(Node node, String... classes){
        node.getStyleClass().clear();
        node.getStyleClass().addAll(classes);
    }

    public static void anchorInAnchorPane(Node node){
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
    }

}
