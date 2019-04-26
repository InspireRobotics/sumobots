package org.inspirerobotics.sumobots.tars;

import org.inspirerobotics.sumobots.Ports;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class RobotServerConnection {

    private final Socket connection;

    public RobotServerConnection() {
        this.connection = getConnection();
    }

    private Socket getConnection() {
        try{
            return new Socket("localhost", Ports.ROBOT_FTP);
        }catch(IOException e){
            throw new SumobotsRuntimeException("Failed to connect to RMS! ", e);
        }
    }

    public OutputStream getOutputStream(){
        try {
            return connection.getOutputStream();
        } catch(IOException e) {
            throw new SumobotsRuntimeException(e);
        }
    }

    public void shutdown() {
        try {
            connection.getOutputStream().flush();
            connection.shutdownOutput();
        } catch(IOException e) {
            throw new SumobotsRuntimeException("Failed to shutdown output!", e);
        }

        close();
        return;
    }

    public void close() {
        try {
            connection.close();
        } catch(IOException e) {
            throw new SumobotsRuntimeException("Failed to close RMS connection! ", e);
        }
    }
}
