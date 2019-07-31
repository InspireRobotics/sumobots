package org.inspirerobotics.sumobots.tars;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

import java.io.*;
import java.util.Arrays;

public class Tars {

    private static final Logger logger = LogManager.getLogger(Tars.class);

    private final String[] args;
    private final String filePath;
    private final String robotIP;

    public Tars(String[] args) {
        this.args = args;

        if(args.length != 2){
            throw new SumobotsRuntimeException("Expected two arguments, found " + args.length + "!");
        }

        this.filePath = args[0];
        this.robotIP = args[1];
    }

    private void run() {
        RobotServerConnection connection = new RobotServerConnection(robotIP);
        InputStream fileStream = getFileStream();

        FileTransferManager fileTransferManager = new FileTransferManager(connection.getOutputStream(), fileStream);
        fileTransferManager.transfer();
        connection.shutdown();
        closeStreams(connection, fileStream);
    }

    private void closeStreams(RobotServerConnection connection, InputStream fileStream)  {
        connection.close();

        try {
            fileStream.close();
        } catch(IOException e) {
            throw new SumobotsRuntimeException("Failed to close file input stream!", e);
        }
    }

    private InputStream getFileStream() {
        File file = new File(filePath);

        if(file.isDirectory()){
            throw new SumobotsRuntimeException("Directory Upload not implemented yet!");
        }

        if(!file.exists()){
            throw new SumobotsRuntimeException("Input File does not exist!");
        }

        return createInputStream(file);
    }

    private InputStream createInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch(FileNotFoundException e) {
            throw new SumobotsRuntimeException("Failed to read file!", e);
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public static void main(String[] args){
        try{
            new Tars(args).run();
        }catch(SumobotsRuntimeException e){
            logger.info("TARS Failed!");
            logger.info("Args: " + Arrays.toString(args));
            logger.info("Cause: " + e.getMessage());
            logger.info("---- Stack Trace ----");
            e.printStackTrace();

            System.exit(1);
        }

        logger.info("Robot code successfully transferred!");
    }
}
