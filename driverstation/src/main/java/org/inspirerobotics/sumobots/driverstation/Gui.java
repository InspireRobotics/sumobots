package org.inspirerobotics.sumobots.driverstation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.Version;
import org.inspirerobotics.sumobots.driverstation.gui.RootPane;
import org.inspirerobotics.sumobots.driverstation.gui.StagePositionManager;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationState;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Gui extends Application implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = LogManager.getLogger(Gui.class);
    private BackendWorker backendWorker;
    private Thread backendThread;

    private Stage stage;
    private RootPane rootPane;

    @Override
    public void init() {
        backendWorker = new BackendWorker(this);
        createBackendWorkerThread();
    }

    private void createBackendWorkerThread() {
        backendThread = new Thread(backendWorker);
        backendThread.setName("Backend Thread");
        backendThread.setDaemon(false);
        backendThread.setUncaughtExceptionHandler(this);
        backendThread.start();
    }

    @Override
    public void start(Stage s) {
        logger.info("initializing gui!");
        Thread.currentThread().setUncaughtExceptionHandler(this);

        stage = s;

        initStage();
        initScene();

        stage.show();
        logger.info("Showing Gui!");
    }

    private void initStage() {
        stage.setTitle("Driverstation: " + Version.VERSION);
        stage.setOnCloseRequest(event -> {
            logger.info("User closed window!");
            stage.hide();
            shutdownAndTerminate(0);
        });

        new StagePositionManager(stage);
    }

    private void initScene() {
        rootPane = new RootPane();
        stage.setScene(rootPane.createScene());
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        String stacktrace = uncaughtExceptionToString(e);
        logger.fatal("Uncaught Exception has occurred on backend thread `{}`: {}", t.getName(), stacktrace);
        shutdownAndTerminate(1);
    }

    private String uncaughtExceptionToString(Throwable e) {
        StringWriter stacktrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stacktrace));

        return stacktrace.toString();
    }

    public void shutdownAndTerminate(int status) {
        logger.fatal("Terminating app with status: " + status);

        Platform.exit();
        stopBackendThread();
        System.exit(status);
    }

    private void stopBackendThread() {
        backendWorker.stopRunning();

        try {
            backendThread.join(1000);

            if (backendThread.isAlive() && backendThread.isInterrupted() == false) {
                logger.warn("Failed to stop backend backend thread!");
            }
        } catch (InterruptedException e) {
            logger.fatal("Backend Thread interrupted while joining: {}", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateDriverstationState(DriverstationState newState) {
        if(stage != null){
            stage.setTitle("Driverstation: " + newState.getCurrentState());
            rootPane.onStateUpdated(newState);
        }
    }
}
