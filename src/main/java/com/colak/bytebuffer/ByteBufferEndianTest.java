package com.colak.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferEndianTest {

    private static final int value = 0x01020304;

    public static void main() {
        ByteBuffer bigEndianBuffer = ByteBuffer.allocate(4);

        // Bytes: 01 02 03 04
        serializeInteger(bigEndianBuffer);

        ByteBuffer littleEndianBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);

        // Bytes: 04 03 02 01
        serializeInteger(littleEndianBuffer);

        // This is another way of writing big endian
        // BB: 02
        // CC: 03
        // DD: 04
        // Extract bytes using bitwise shifts
        // Extract high and low words
        int highWord = (value >> 16) & 0xFFFF;
        int lowWord = value & 0xFFFF;

        // Extract bytes from the words
        int bb = highWord & 0xFF;      // Low byte of high word
        int cc = (lowWord >> 8) & 0xFF; // High byte of low word
        int dd = lowWord & 0xFF;       // Low byte of low word


        // Print the extracted bytes
        System.out.printf("BB: %02X\n", bb);
        System.out.printf("CC: %02X\n", cc);
        System.out.printf("DD: %02X\n", dd);
    }

    private static void serializeInteger(ByteBuffer byteBuffer) {
        byteBuffer.putInt(value);

        byte[] fullArray = byteBuffer.array();

        System.out.print("Bytes: ");
        for (byte b : fullArray) {
            System.out.printf("%02X ", b);
        }
        System.out.println();
    }
}
