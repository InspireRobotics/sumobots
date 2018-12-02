package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;

public interface RequestHandler {

    NanoHTTPD.Response handleRequest(NanoHTTPD.IHTTPSession session);

}
