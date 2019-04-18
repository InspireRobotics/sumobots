package org.inspirerobotics.sumobots.rms.ftp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileServerTests {

    private final FileServer server = new FileServer();

    @Test
    void currentConnectionEmptyByDefaultTest() {
        Assertions.assertFalse(server.getCurrentConnection().isPresent());
    }
}
