package org.inspirerobotics.sumobots.rms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.rms.ftp.FileServer;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class RMS {

    private static final Logger logger = LogManager.getLogger(RMS.class);

    private final FileServer fileServer;
    private ProcessManager currentProcess;

    public RMS() {
        fileServer = new FileServer();

        startProcess();
    }

    private void startProcess() {
        try {
            logger.info("Starting robot code!");
            currentProcess = new ProcessManager();
        } catch(IOException e) {
            throw new SumobotsRuntimeException("Failed to start robot code!", e);
        }
    }

    private void update() {
        Optional<Socket> connection = fileServer.getConnection();

        if(connection.isPresent()){
            logger.info("Code install request received!");
            currentProcess.destroy();
            fileServer.transferFile(connection.get());
        }

        if(!currentProcess.isAlive()){
            startProcess();
        }

        currentProcess.update();
    }

    public static void main(String[] args){
        logger.info("Started RMS!");

        FileUtils.checkDirectory();
        RMS rms = new RMS();

        try{
            while(true){
                rms.update();
            }
        }catch(Exception e){
            logger.error("Error occurred while running RMS: {}", e);
            e.printStackTrace();
        }
    }
}
