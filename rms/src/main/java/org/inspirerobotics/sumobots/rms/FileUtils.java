package org.inspirerobotics.sumobots.rms;

import org.apache.logging.log4j.LogManager;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

import java.io.File;

public class FileUtils {

    private FileUtils(){}

    public static String rootDirectoryPath(){
        return System.getProperty("user.home") + "/sumobots/";
    }

    public static void checkDirectory() {
        File file = new File(rootDirectoryPath());

        if(file.exists()){
            if(!file.isDirectory()){
                throw new SumobotsRuntimeException("sumo folder exists, but is not a directory!");
            }
        }else{
            LogManager.getLogger(FileUtils.class).info("Sumo install folder not found! Creating now!");

            if(!file.mkdir()){
                throw new SumobotsRuntimeException("Failed to create install directory!");
            }
        }
    }
}
