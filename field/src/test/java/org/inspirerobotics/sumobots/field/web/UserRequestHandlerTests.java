package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserRequestHandlerTests {

    @Test
    public void handleRequestTest(){
        UserRequestHandler handler = new UserRequestHandler();
        NanoHTTPD.Response response = handler.handleRequest(null);

        Assertions.assertEquals(response.getStatus(), NanoHTTPD.Response.Status.NOT_IMPLEMENTED);
    }
}
