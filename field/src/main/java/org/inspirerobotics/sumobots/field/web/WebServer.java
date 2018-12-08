package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.field.Field;

import java.io.IOException;

public class WebServer extends NanoHTTPD {

    public static final Response.Status NOT_FOUND = Response.Status.NOT_FOUND;
    public static final Response.Status INTERNAL_ERROR = Response.Status.INTERNAL_ERROR;
    public static final Response.Status NOT_IMPLEMENTED = Response.Status.NOT_IMPLEMENTED;
    public static final Response.Status OK = Response.Status.OK;
    public static final String MIME_JSON = "application/json";

    private static final Logger logger = LogManager.getLogger(WebServer.class);

    private final Field field;
    private final StaticFileRequestHandler staticFileHandler;
    private final UserRequestHandler userResponseHandler;
    private final FieldRequestHandler fieldRequestHandler;

    public WebServer(Field field) {
        super(8000);

        this.field = field;
        this.userResponseHandler = new UserRequestHandler();
        this.staticFileHandler = new StaticFileRequestHandler();
        this.fieldRequestHandler = new FieldRequestHandler(field);
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

        if(url.startsWith("/field")){
            return handle(fieldRequestHandler, session);
        }

        if(url.startsWith("/kill")){
            return killServerRequest(session);
        }

        return error(NOT_FOUND, "Route not found: " + url);
    }

    private Response killServerRequest(IHTTPSession session) {
        logger.info("Kill request received, killing field system.");

        field.stop();
        return null;
    }

    private Response handle(RequestHandler handler, IHTTPSession session){
        return handler.handleRequest(session);
    }

    public static Response error(Response.Status status, String desc) {
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>Error: " + status.getRequestStatus() + " </h1>");
        builder.append("<p>" + desc + "</p>");

        logger.error("Error ({}): {}", status.getRequestStatus(), desc);

        return newFixedLengthResponse(builder.toString());
    }
}
