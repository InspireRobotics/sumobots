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
            socket.close();
        } catch(IOException e) {
            throw new SumobotsRuntimeException("I/O Error while downloading code!", e);
        }

        logger.info("Finished saving robot code!");
    }

    private static void saveData(Socket socket, OutputStream fileOutputStream) throws IOException {
        InputStream inputStream = socket.getInputStream();
        socket.setSoTimeout(5000);

        while(transferPacket(fileOutputStream, inputStream)) { }
    }

    static boolean transferPacket(OutputStream fileOutputStream, InputStream inputStream) throws IOException {
        byte[] packetSizeData = new byte[4];
        readArrayFromInputStream(inputStream, packetSizeData);

        int packetSize = ByteUtils.bytesToInt(packetSizeData);

        if(packetSize == Integer.MAX_VALUE || packetSize < 0){
            logger.info("Reached end of data: " + packetSize);
            return false;
        }

        byte[] packetData = new byte[packetSize];

        readArrayFromInputStream(inputStream, packetData);
        fileOutputStream.write(packetData);
        fileOutputStream.flush();



        return true;
    }

    private static void readArrayFromInputStream(InputStream inputStream, byte[] packetData) throws IOException {
        int bytesRead = 0;

        while(bytesRead < packetData.length && bytesRead >= 0){
             int tempBytesRead = inputStream.read(packetData, bytesRead, packetData.length - bytesRead);

            bytesRead += tempBytesRead;
        }
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
