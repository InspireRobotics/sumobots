package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.VisibleForTesting;

import java.awt.*;

public class StagePositionManager{

    public enum WindowMode{FLOAT, DOCKED}

    private static final Logger logger = LogManager.getLogger(StagePositionManager.class);
    private static StagePositionManager instance;

    private final Stage stage;
    private WindowMode currentMode = WindowMode.FLOAT;

    public StagePositionManager(Stage stage) {
        checkIfFirstInstance();

        instance = this;
        this.stage = stage;

        this.stage.xProperty().addListener((observable, oldVal, newVal) -> update());
        this.stage.widthProperty().addListener((observable, oldVal, newVal) -> update());
        this.stage.setOnShowing(event -> update());
    }

    private void checkIfFirstInstance() {
        if(instance != null)
            throw new SumobotsRuntimeException("StagePositionManager can only be created once!");
    }

    public void update(){
        if(currentMode == WindowMode.DOCKED){
            updateDocked();
        }else{
            stage.setResizable(true);
        }
    }

    public void updateDocked() {
        stage.setResizable(false);
        stage.sizeToScene();

        updateX();
        updateWidth();

    }

    private void updateWidth() {
        this.stage.setWidth(getScreenWidth());
    }

    private double getScreenWidth() {
        if(GraphicsEnvironment.isHeadless())
            return 0;

        return Screen.getPrimary().getVisualBounds().getWidth();
    }


    public void updateX() {
        this.stage.setX(0);
        this.stage.setY(getScreenHeight() - stage.getHeight());
    }

    private double getScreenHeight() {
        if(GraphicsEnvironment.isHeadless())
            return 0;

        return Screen.getPrimary().getVisualBounds().getHeight();
    }

    public void setCurrentMode(WindowMode currentMode) {
        this.currentMode = currentMode;
        logger.info("Setting window mode: " + currentMode);

        if(currentMode == WindowMode.DOCKED) {
            updateDocked();
        }else{
            stage.setResizable(true);
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    @VisibleForTesting
    static void setInstance(StagePositionManager instance) {
        StagePositionManager.instance = instance;
    }

    @VisibleForTesting
    public WindowMode getCurrentMode() {
        return currentMode;
    }

    public static StagePositionManager getInstance() {
        return instance;
    }
}
