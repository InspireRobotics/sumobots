package org.inspirerobotics.sumobots;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VersionTests {

    @Test
    void basicGetVersionFromStringTest() {
        String version = "1.2.3";
        int[] actual = Version.getVersionFromString(version);
        int[] expected = new int[]{1, 2, 3};

        Assertions.assertArrayEquals(actual, expected);
    }

    @Test
    void longGetVersionFromStringTest() {
        String version = "66.4324.435.234.235";
        int[] actual = Version.getVersionFromString(version);
        int[] expected = new int[]{66, 4324, 435, 234, 235};

        Assertions.assertArrayEquals(actual, expected);
    }

    @Test
    void negativeGetVersionFromStringFailsTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () -> Version.getVersionFromString("0.-5.0"));
    }

    @Test
    void nonNumberGetVersionFromStringFailsTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () -> Version.getVersionFromString("1.b.5"));
    }
}
