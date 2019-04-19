package org.inspirerobotics.sumobots.rms.ftp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.ByteUtils;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

import java.io.*;
import java.net.Socket;

public class FileDownloadManager {

    private static final Logger logger = LogManager.getLogger(FileDownloadManager.class);

    public static void download(Socket socket, File installDir) {
        logger.info("Saving robot code!");

        try {
            File robotJar = createRobotJar(installDir);
            OutputStream fileOutputStream = createFileOutputStream(robotJar);

            saveData(socket, fileOutputStream);
            fileOutputStream.close();
        } catch(IOException e) {
            throw new SumobotsRuntimeException("I/O Error while downloading code!", e);
        }

        logger.info("Finished saving robot code!");
    }

    private static void saveData(Socket socket, OutputStream fileOutputStream) throws IOException {
        InputStream inputStream = socket.getInputStream();
        socket.setSoTimeout(2500);

        while(transferPacket(fileOutputStream, inputStream)) { }
    }

    private static boolean transferPacket(OutputStream fileOutputStream, InputStream inputStream) throws IOException {
        byte[] packetSizeData = new byte[4];

        if(inputStream.read(packetSizeData) != 4)
            return false;

        int packetSize = ByteUtils.bytesToInt(packetSizeData);
        byte[] packetData = new byte[packetSize];

        if(inputStream.read(packetData) != packetSize)
            return false;

        fileOutputStream.write(packetData);

        return true;
    }

    private static OutputStream createFileOutputStream(File robotJar) throws IOException {
        return new FileOutputStream(robotJar);
    }

    private static File createRobotJar(File installDir) throws IOException {
        File robotJar = new File(installDir, "code.jar");

        if(!robotJar.createNewFile())
            throw new SumobotsRuntimeException("Tried to create robot jar, but it already existed");

        return robotJar;
    }
}
