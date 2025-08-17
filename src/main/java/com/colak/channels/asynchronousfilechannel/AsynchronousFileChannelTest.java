package com.colak.channels.asynchronousfilechannel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.util.concurrent.Future;

/**
 * See <a href="https://medium.com/@lfoster49203/advanced-java-i-o-delving-deep-into-nio-nio2-asynchronous-channels-and-sockets-87c291441ce7">...</a>
 */
@Slf4j
public class AsynchronousFileChannelTest {

    public static void main(String[] args) {
        try (AsynchronousFileChannel asyncChannel = AsynchronousFileChannel.open(Paths.get("file.txt"))) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Future<Integer> operation = asyncChannel.read(buffer, 0);
            while (!operation.isDone()) ; // Wait until read operation is complete

            buffer.flip();
            while (buffer.hasRemaining()) {
                char c = (char) buffer.get();
                System.out.print(c);
            }
        } catch (IOException exception) {
            log.error("Exception : ", exception);
        }
    }
}
