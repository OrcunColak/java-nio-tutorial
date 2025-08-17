package com.colak.channels.memorymapped;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class MemoryMappedFileInplaceTest {

    public static void main() {
        String path = "src/main/resources/test.txt";
        try (RandomAccessFile file = new RandomAccessFile(path, "rw");
             FileChannel channel = file.getChannel()) {

            long fileSize = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);

            // Direct memory access - no system calls
            while (buffer.hasRemaining()) {
                byte data = buffer.get();
                // Process directly in mapped memory
                buffer.put(buffer.position() - 1, transformByte(data));
            }

            buffer.force(); // Sync to disk
        } catch (
                IOException exception) {
            log.error("Exception ", exception);
        }
    }

    private static byte transformByte(byte data) {
        return 0;
    }
}
