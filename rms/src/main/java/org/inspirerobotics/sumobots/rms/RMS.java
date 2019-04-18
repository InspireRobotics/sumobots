package org.inspirerobotics.sumobots.rms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.rms.ftp.FileServer;

public class RMS {

    private static final Logger logger = LogManager.getLogger(RMS.class);

    public static void main(String[] args){
        logger.info("Started RMS!");

        FileUtils.checkDirectory();

        new FileServer().run();
    }
}
