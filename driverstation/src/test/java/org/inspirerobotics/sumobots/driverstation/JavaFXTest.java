package org.inspirerobotics.sumobots.driverstation;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.swing.*;

public class JavaFXTest implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        SwingUtilities.invokeLater(() ->{
            new JFXPanel();
        });

        boolean initializing = true;

        while(initializing){
            try{
                Platform.runLater(() -> {});
                initializing = false;
            }catch(IllegalStateException e){
                Thread.yield();
            }
        }
    }
}
