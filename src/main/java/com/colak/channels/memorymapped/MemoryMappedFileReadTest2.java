package com.colak.channels.memorymapped;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
public class MemoryMappedFileReadTest2 {

    public static void main() throws IOException {
        Path path = Path.of("src/main/resources/test.txt");
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            long fileSize = fileChannel.size();
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);

            readAll(buffer);
        }
    }

    private static void readAll(MappedByteBuffer buffer) {
        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer);
        log.info(charBuffer.toString());
    }
}
