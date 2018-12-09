package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class StaticFileRequestHandlerTests {

    private StaticFileRequestHandler handler = new StaticFileRequestHandler();

    @Test
    void notFoundErrorTest() throws IOException {
        NanoHTTPD.Response response = handler.handleRequestWithUrl("fooBar");
        WebServerTestUtils.assertResponseData(response,
                "<h1>Error: 404 </h1><p>Failed to find file at path fooBar</p>");
    }
}
