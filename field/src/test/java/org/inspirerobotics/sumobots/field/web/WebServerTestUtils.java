package org.inspirerobotics.sumobots.field.web;

import fi.iki.elonen.NanoHTTPD;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WebServerTestUtils {

    public static void assertResponseData(NanoHTTPD.Response response, String expectedData) throws IOException {
        List<String> lines = getOutputLines(response);
        String actualData = lines.get(lines.size() - 1);

        Assertions.assertEquals(expectedData, actualData);
    }

    private static List<String> getOutputLines(NanoHTTPD.Response response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getData()));
        return readerToStringList(reader);
    }

    private static List<String> readerToStringList(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = "";
        while (line != null) {
            line = reader.readLine();
            if (line != null) {
                lines.add(line.trim());
            }
        }
        return lines;
    }
}
