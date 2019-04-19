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
            while(!transferData()){ }
        } catch(IOException e) {
            throw new SumobotsRuntimeException("I/O failure while transferring file!", e);
        }
    }

    private boolean transferData() throws IOException {
        byte[] bytes = new byte[1024];

        int bytesRead = fileInputStream.read(bytes, 0, 1024);

        if(bytesRead == -1){
            return false;
        }

        byte[] dataSize = ByteUtils.intToBytes(bytesRead);

        socketOutputStream.write(dataSize);
        socketOutputStream.write(bytes, 0, bytesRead);

        return true;
    }
}
