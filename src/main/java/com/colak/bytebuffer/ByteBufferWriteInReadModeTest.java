package com.colak.bytebuffer;

import java.nio.ByteBuffer;

// 1. A ByteBuffer of size 10 is created, and two bytes are written to it using put().
// 2. flip() is called, which switches the buffer to read mode.
// 3. After the buffer is flipped, we attempt to write more data with put().
// 4. Since the buffer is in read mode, it does not throw an exception, but it silently overwrites memory beyond the limit.
// 5. Finally, when we flip the buffer again to read and print its contents, we observe that the written data will have overwritten
//    the earlier data in unintended locations.
class ByteBufferTestWriteInReadModeTest {

    public static void main() {
        // Allocate a ByteBuffer with space for 10 bytes
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // Write some data into the buffer
        buffer.put((byte) 1);
        buffer.put((byte) 2);

        // Flip to prepare for reading
        buffer.flip();

        // Try to write again while the buffer is in read mode
        buffer.put((byte) 3); // This will overwrite data silently

        // Show buffer content
        buffer.flip(); // Reset to read mode
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}