package org.inspirerobotics.sumobots.rms;

import java.nio.ByteBuffer;

public class ByteUtils {

    public static byte[] intToBytes(int value){
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static int bytesToInt(byte[] value){
        if(value.length != 4)
            throw new IllegalArgumentException("Byte array must be of length four!");

        return ByteBuffer.wrap(value).getInt();
    }
}
