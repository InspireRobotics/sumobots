package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.field.Field;

import java.io.IOException;

public class WebServer extends NanoHTTPD {

    public static final int FILE_NOT_FOUND = 404;
    public static final int INTERNAL_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    private static final Logger logger = LogManager.getLogger(WebServer.class);

    private final Field field;
    private final StaticFileRequestHandler staticFileHandler;
    private final UserRequestHandler userResponseHandler;

    public WebServer(Field field) {
        super(8000);

        this.field = field;
        this.userResponseHandler = new UserRequestHandler();
        this.staticFileHandler = new StaticFileRequestHandler();
    }

    public void start() throws IOException {
        start(500, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String url = session.getUri();
        logger.debug("Handling request: " + url);

        if(url.equals("/"))
            return staticFileHandler.handleRequestWithUrl("/static/index.html");

        if(url.startsWith("/static")){
            return handle(staticFileHandler, session);
        }

        if(url.startsWith("/user")){
            return handle(userResponseHandler, session);
        }

        if(url.startsWith("/kill")){
            return killServerRequest(session);
        }

        return error(404, "Route not found: " + url);
    }

    private Response killServerRequest(IHTTPSession session) {
        logger.info("Kill request received, killing field system.");

        field.stop();
        return null;
    }

    private Response handle(RequestHandler handler, IHTTPSession session){
        return handler.handleRequest(session);
    }

    public static Response error(int errorCode, String desc) {
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>Error: " + errorCode + " </h1>");
        builder.append("<p>" + desc + "</p>");

        logger.error("Error ({}): {}", errorCode, desc);

        return newFixedLengthResponse(builder.toString());
    }
}
