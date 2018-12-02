package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StaticFileRequestHandler implements RequestHandler {

    public NanoHTTPD.Response handleRequest(NanoHTTPD.IHTTPSession session) {
        return handleRequestWithUrl(session.getUri());
    }

    public NanoHTTPD.Response handleRequestWithUrl(String url){
        InputStream input = urlToInputStream(url);

        if(input == null)
            return WebServer.error(WebServer.FILE_NOT_FOUND, "Failed to find file at path " + url);

        return loadSrc(input, url);
    }

    private  NanoHTTPD.Response loadSrc(InputStream inputStream, String path) {
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            while(br.ready()){
                buffer.append(br.readLine() + "\n");
            }
        }catch(IOException e){
            WebServer.error(WebServer.INTERNAL_ERROR, "Failed to load file at path: " + path);
        }

        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, getMimeType(path), buffer.toString());
    }

    private String getMimeType(String path) {
        if(path.endsWith(".css")){
            return "text/css";
        }else if(path.endsWith(".html")){
            return "text/html";
        }else if(path.endsWith(".js")){
            return "text/javascript";
        }

        System.out.println("Unknown file type for path: " + path);
        return NanoHTTPD.MIME_PLAINTEXT;
    }

    private InputStream urlToInputStream(String url) {
        return getClass().getResourceAsStream(url);
    }
}
