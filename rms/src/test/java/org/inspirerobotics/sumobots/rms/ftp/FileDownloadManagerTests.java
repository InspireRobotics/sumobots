package org.inspirerobotics.sumobots.rms.ftp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FileDownloadManagerTests {

    @Test
    void readPacketBasicTest() throws IOException {
        byte[] input = new byte[]{0, 0, 0, 5, -5, 123, 0, 66, 23};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        FileDownloadManager.transferPacket(outputStream, inputStream);

        Assertions.assertArrayEquals(new byte[]{-5, 123, 0, 66, 23}, outputStream.toByteArray());
    }

    @Test
    void readPacketLessThan4SizeBytesReturnsFalseTest() throws IOException{
        byte[] fileData = new byte[]{3, 14, 127};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);

        Assertions.assertFalse(FileDownloadManager.transferPacket(null, inputStream));
    }
}
