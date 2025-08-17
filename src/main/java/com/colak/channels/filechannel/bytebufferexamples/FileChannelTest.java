package com.colak.channels.filechannel.bytebufferexamples;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// See https://medium.com/@lfoster49203/advanced-java-i-o-delving-deep-into-nio-nio2-asynchronous-channels-and-sockets-87c291441ce7
// Example that shows how to read from source FileChannel into ByteBuffer and write to TargetFileChannel using write() method
@Slf4j
public class FileChannelTest {

    public static void main(String[] args) {
        try (FileChannel inChannel = FileChannel.open(Paths.get("source.txt"), StandardOpenOption.READ);
             FileChannel outChannel = FileChannel.open(Paths.get("destination.txt"),
                     StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1) {

                // Prepare the buffer to be drained
                buffer.flip();
                outChannel.write(buffer);

                // Prepare the buffer for reading
                buffer.clear();
            }
        } catch (IOException exception) {
            log.error("Exception : ", exception);
        }
    }
}
