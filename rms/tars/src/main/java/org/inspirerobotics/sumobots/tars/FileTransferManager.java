package org.inspirerobotics.sumobots.tars;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.ByteUtils;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileTransferManager {

    private static final Logger logger = LogManager.getLogger(FileTransferManager.class);

    private final OutputStream socketOutputStream;
    private final InputStream fileInputStream;

    public FileTransferManager(OutputStream socketStream, InputStream fileInput) {
        this.socketOutputStream = socketStream;
        this.fileInputStream = fileInput;
    }

    public void transfer() {
        logger.info("Transferring file!");

        try {
            while(transferData()){ }
        } catch(IOException e) {
            throw new SumobotsRuntimeException("I/O failure while transferring file!", e);
        }
        logger.info("Finished sending robot code!");
    }

    boolean transferData() throws IOException {
        byte[] bytes = new byte[1024];

        int bytesRead = readArrayFromInputStream(bytes);

        if(bytesRead == -1){
            socketOutputStream.write(ByteUtils.intToBytes(Integer.MAX_VALUE));
            return false;
        }

        byte[] dataSize = ByteUtils.intToBytes(bytesRead);

        socketOutputStream.write(dataSize);
        socketOutputStream.write(bytes, 0, bytesRead);
        socketOutputStream.flush();

        return true;
    }

    private int readArrayFromInputStream(byte[] packetData) throws IOException {
        int bytesRead = 0;

        while(bytesRead < packetData.length && bytesRead >= 0){
            int tempBytesRead = fileInputStream.read(packetData, bytesRead, packetData.length - bytesRead);

            if(tempBytesRead == -1 && bytesRead == 0)
                return -1;

            if(tempBytesRead == -1)
                break;

            bytesRead += tempBytesRead;
        }

        return bytesRead;
    }
}
