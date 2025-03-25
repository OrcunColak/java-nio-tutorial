package com.colak.bytebuffer;

import java.nio.ByteBuffer;

// 1. Buffer Allocation: A ByteBuffer of 10 bytes is allocated.
// 2. Write Data: Three bytes are written to the buffer.
// 3. Flip to Read Mode: The buffer is flipped to prepare for reading.
// 4. Read Data: Two bytes are read from the buffer.
// 5. Compact: compact() is called to preserve the unread data and create space for new data.
// 6. Write New Data: New bytes are written after compacting.
// 7. Flip to Read Again: The buffer is flipped back for reading.
// 8. Verify Results: The program verifies that the unread data is preserved, and the new data is correctly written after the compact.
class ByteBufferCompactTest  {

    public static void main() {
        // Step 1: Allocate a ByteBuffer with capacity of 10 bytes
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // Step 2: Write some data to the buffer
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);

        // Step 3: Flip the buffer to prepare for reading
        buffer.flip();

        // Step 4: Read two bytes from the buffer
        System.out.println("Read byte 1: " + buffer.get());
        System.out.println("Read byte 2: " + buffer.get());

        // Step 5: Call compact() to preserve unread data and prepare space for new writes
        buffer.compact();

        // Step 6: Verify the state of the buffer after compact
        System.out.println("Position after compact: " + buffer.position());  // Should be 2 (first unread byte)
        System.out.println("Limit after compact: " + buffer.limit());  // Should be 10 (buffer capacity)

        // Step 7: Write new data to the buffer (after compact)
        buffer.put((byte) 4);
        buffer.put((byte) 5);

        // Step 8: Reset the position and flip to read mode again
        buffer.flip();

        // Step 9: Verify that the unread data is still preserved, and new data has been written
        System.out.println("Read byte 3: " + buffer.get());  // Unread data from before compact
        System.out.println("Read byte 4: " + buffer.get());  // New data written after compact
        System.out.println("Read byte 5: " + buffer.get());  // Another new byte after compact

        // Step 10: Verify that the buffer is now fully read
        System.out.println("Buffer has remaining: " + buffer.hasRemaining());  // Should be false
    }
}