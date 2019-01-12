package org.inspirerobotics.sumobots.robot.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.Ports;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.VisibleForTesting;
import org.inspirerobotics.sumobots.robot.util.ExceptionHandlers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class DriverstationServer implements Runnable{

    private static final Logger logger = LogManager.getLogger(DriverstationServer.class);
    private ServerSocketChannel channel;

    @Override
    public void run() {
        init();
        logger.info("Driverstation server started!");
        while(!Thread.currentThread().isInterrupted()){
            accept();
        }
    }

    private void accept() {
        try{
            handle(channel.accept());
        }catch (IOException e){
            logger.fatal("DS server had an error while accepting!", e);
            throw new SumobotsRuntimeException(e);
        }
    }

    @VisibleForTesting
    void handle(SocketChannel connection) throws IOException{
        if(Driverstation.getInstance().isConnected()){
            logger.warn("Connection attempted while already connected: " + connection.getRemoteAddress());
            connection.close();
        }else{
            Driverstation.getInstance().setConnection(Optional.of(new DriverstationConnection(connection)));

            logger.info("Connected to driverstation: " + connection.getRemoteAddress());
        }
    }

    private void init() {
        try {
            channel = ServerSocketChannel.open();
            channel.configureBlocking(true);
            channel.bind(new InetSocketAddress("0.0.0.0", Ports.ROBOT));
        } catch (IOException e) {
            logger.fatal("Failed to start DS server!", e);
            throw new SumobotsRuntimeException(e);
        }
    }

    public static Thread createAndRun(){
        Thread thread = new Thread(new DriverstationServer());
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler(ExceptionHandlers.nonRobotHandler());
        thread.setName("Driverstation Server");
        thread.start();

        return thread;
    }
}
