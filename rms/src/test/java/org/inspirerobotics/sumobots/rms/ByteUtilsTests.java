package org.inspirerobotics.sumobots.rms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ByteUtilsTests {

    @ParameterizedTest
    @ValueSource(ints = {-25, 12, 0, 124534, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void intByteConversionTest(int input) {
        byte[] bytes = ByteUtils.intToBytes(input);
        int actual = ByteUtils.bytesToInt(bytes);

        Assertions.assertEquals(input, actual);
    }
}
