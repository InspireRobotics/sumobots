package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import org.inspirerobotics.sumobots.VisibleForTesting;
import org.inspirerobotics.sumobots.driverstation.gui.style.StyleManager;
import org.inspirerobotics.sumobots.driverstation.gui.style.Styleable;

public class LogPane extends AnchorPane implements Styleable {

    private final TitledPane titledPane;
    private final TextArea textArea;

    public LogPane() {
        this.textArea = new TextArea();
        this.textArea.setEditable(false);
        this.textArea.setId("logText");

        this.titledPane = new TitledPane();
        this.titledPane.setCollapsible(false);
        this.titledPane.setId("logPane");
        this.titledPane.setText("Robot Log");
        this.titledPane.setContent(textArea);

        GuiUtils.anchorInAnchorPane(titledPane);
        StyleManager.getInstance().addChild(this);

        this.getChildren().add(titledPane);
        reset();
    }

    @Override
    public String getStylesheetName() {
        return "log_pane";
    }

    public void appendLog(String line){
        textArea.appendText(line + "\n");
    }

    public void reset() {
        textArea.clear();
    }

    @VisibleForTesting
    TextArea getTextArea() {
        return textArea;
    }
}
