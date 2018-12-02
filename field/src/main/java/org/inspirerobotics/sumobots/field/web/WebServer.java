package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;

public class WebServer extends NanoHTTPD {

    public static final int FILE_NOT_FOUND = 404;
    public static final int INTERNAL_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;

    private final StaticFileRequestHandler staticFileHandler;
    private final UserRequestHandler userResponseHandler;

    public WebServer() {
        super(8000);

        userResponseHandler = new UserRequestHandler();
        staticFileHandler = new StaticFileRequestHandler();
    }

    public void start() throws IOException {
        start(500, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String url = session.getUri();
        System.out.println("Handling request: " + url);

        if(url.equals("/"))
            return staticFileHandler.handleRequestWithUrl("/static/index.html");

        if(url.startsWith("/static")){
            return handle(staticFileHandler, session);
        }

        if(url.startsWith("/user")){
            return handle(userResponseHandler, session);
        }

        return error(404, "Route not found: " + url);
    }

    private Response handle(RequestHandler handler, IHTTPSession session){
        return handler.handleRequest(session);
    }

    public static Response error(int errorCode, String desc) {
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>Error: " + errorCode + " </h1>");
        builder.append("<p>" + desc + "</p>");

        System.err.printf("Error (%d): %s\n", errorCode, desc);

        return newFixedLengthResponse(builder.toString());
    }
}
