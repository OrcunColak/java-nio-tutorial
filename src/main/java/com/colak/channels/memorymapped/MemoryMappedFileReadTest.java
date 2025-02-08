package com.colak.channels.memorymapped;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
@UtilityClass
public class MemoryMappedFileReadTest {

    public static void main() {
        try (
                // Create a RandomAccessFile for reading
                RandomAccessFile file = new RandomAccessFile("src/main/resources/test.txt", "r");
                // Get the file channel
                FileChannel fileChannel = file.getChannel()) {

            // Create a memory-mapped buffer that maps the first FILE_SIZE bytes of the file
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            readAll(buffer);

            buffer.rewind();

            readLines(buffer);

        } catch (IOException exception) {
            log.error("Exception " , exception);
        }
    }

    private static void readLines(MappedByteBuffer buffer) {
        // Read the file line by line
        StringBuilder line = new StringBuilder();
        while (buffer.hasRemaining()) {
            char c = (char) buffer.get();
            if (c == '\n') {  // Newline character detected, print the line
                log.info(line.toString());
                line.setLength(0); // Clear the StringBuilder for the next line
            } else if (c != '\r') { // Ignore carriage returns (for Windows line endings)
                line.append(c); // Add character to the current line
            }
        }

        // Print any remaining content after the last newline
        if (!line.isEmpty()) {
            log.info(line.toString());
        }
    }

    private static void readAll(MappedByteBuffer buffer) {
        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer);
        log.info(charBuffer.toString());
    }
}
