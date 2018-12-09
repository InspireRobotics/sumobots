package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class WebServerTests{

    @Test
    void basicErrorResponseTest() throws IOException {
        NanoHTTPD.Response response = WebServer.error(NanoHTTPD.Response.Status.NOT_FOUND, "fooBar");
        WebServerTestUtils.assertResponseData(response, "<h1>Error: 404 </h1><p>fooBar</p>");
    }
}
