package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;

public class UserRequestHandler implements RequestHandler{

    @Override
    public NanoHTTPD.Response handleRequest(NanoHTTPD.IHTTPSession session) {
        return WebServer.error(WebServer.NOT_IMPLEMENTED, "not implemented!");
    }
}
