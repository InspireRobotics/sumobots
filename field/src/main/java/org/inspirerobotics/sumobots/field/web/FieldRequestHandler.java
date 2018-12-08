package org.inspirerobotics.sumobots.field.web;

import com.google.gson.Gson;
import fi.iki.elonen.NanoHTTPD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.field.Field;
import org.inspirerobotics.sumobots.field.driverstation.DriverstationConnection;
import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.io.IOException;
import java.util.ArrayList;

public class FieldRequestHandler implements RequestHandler{

    private static final Logger logger = LogManager.getLogger(FieldRequestHandler.class);
    private final Gson gson = new Gson();
    private final Field field;

    public FieldRequestHandler(Field field) {
        this.field = field;
    }

    @Override
    public NanoHTTPD.Response handleRequest(NanoHTTPD.IHTTPSession session) {
        return WebServer.newFixedLengthResponse(WebServer.OK, WebServer.MIME_JSON, generateJSON());
    }

    private String generateJSON() {
        ArrayList<DriverstationConnectionInfo> data = getDriverstationData();

        return gson.toJson(data);
    }

    private ArrayList<DriverstationConnectionInfo> getDriverstationData() {
        ArrayList<DriverstationConnectionInfo> data = new ArrayList<>();

        synchronized (field.getDsManager()){
            for(DriverstationConnection conn : field.getDsManager().getConnections()){
                if(conn.getPipe().isPresent()){
                    data.add(new DriverstationConnectionInfo(conn.getPipe().get()));
                }
            }
        }

        return data;
    }

    class DriverstationConnectionInfo {
        private String ip;
        private int ping;

        public DriverstationConnectionInfo(SocketPipe conn) {
            try{
                this.ip = conn.getSocket().getRemoteAddress().toString();
                this.ping = conn.getPing();
            }catch (IOException e){
                logger.error("Failed to gather data from socket pipe: " + conn);
            }

        }

        public int getPing() {
            return ping;
        }

        public String getIp() {
            return ip;
        }
    }
}
