package org.inspirerobotics.sumobots.tars;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FileTransferManagerTests {

    @Test
    void transferDataTest() throws IOException {
        byte[] fileData = new byte[]{3, 14, 127, 0, -5};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        FileTransferManager manager = new FileTransferManager(outputStream, inputStream);
        manager.transferData();

        byte[] output = outputStream.toByteArray();
        byte[] expected = new byte[]{0, 0, 0, 5, 3, 14, 127, 0, -5};

        Assertions.assertArrayEquals(expected, output);
    }
}
