package org.inspirerobotics.sumobots.rms.ftp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.Ports;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.rms.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Optional;

public class FileServer {

    private static final Logger logger = LogManager.getLogger(FileServer.class);

    private final ServerSocket serverSocket;

    private Optional<Socket> currentConnection = Optional.empty();

    public FileServer() {
        this.serverSocket = createServerSocket();
    }

    public Optional<Socket> getConnection() {
        try {
            serverSocket.setSoTimeout(5);
            Socket socket = serverSocket.accept();

            logger.info("Accepted Socket: {}", socket.getRemoteSocketAddress());

            return Optional.of(socket);
        } catch(SocketTimeoutException e){
            // Do nothing if there are no requests
        }catch(IOException e) {
            logger.info("Error while accepting socket: {}", e);
        }

        return Optional.empty();
    }

    public void transferFile(Socket socket) {
        try{
            logger.info("Beginning to install robot code!");
            File codeDir = new File(FileUtils.rootDirectoryPath() + "/robot-code/");

            deleteOld(codeDir);
            createDirectory(codeDir);
            saveNewRobotCode(socket, codeDir);
            logger.info("Successfully installed robot code!");
        }catch(SumobotsRuntimeException e){
            logger.warn("Failed to install robot code: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveNewRobotCode(Socket socket, File installDir) {
        FileDownloadManager.download(socket, installDir);
    }

    private void createDirectory(File codeDir) {
        logger.info("Creating code install directory!");

        if(!codeDir.mkdir()){
            throw new SumobotsRuntimeException("Failed to create code directory!");
        }
    }

    private void deleteOld(File file) {
        if(file.exists()){
            logger.info("Deleting old code...");

            if(!deleteDir(file)){
               throw new SumobotsRuntimeException("Failed to delete old code!");
            }
        }
    }

    private boolean deleteDir(File file) {
        logger.debug("Deleting file: " + file.getPath());

        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if(!deleteDir(f)){
                    return false;
                }
            }
        }
        return file.delete();
    }

    private ServerSocket createServerSocket() {
        try {
            ServerSocket s = new ServerSocket(Ports.ROBOT_FTP, 1);

            return s;
        } catch(IOException e) {
            e.printStackTrace();

            throw new SumobotsRuntimeException("Failed to create FTP socket!", e);
        }
    }

    public Optional<Socket> getCurrentConnection() {
        return currentConnection;
    }
}
