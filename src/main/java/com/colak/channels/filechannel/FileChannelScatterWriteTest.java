package com.colak.channels.filechannel;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class FileChannelScatterWriteTest {

    public static void main(String[] args) {

        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(1024);
        ByteBuffer footer = ByteBuffer.allocate(10);
        ByteBuffer[] buffers = {header, body, footer};


        try (FileOutputStream fileOutputStream = new FileOutputStream("example.txt");
             FileChannel channel = fileOutputStream.getChannel()) {
            channel.write(buffers);

        } catch (IOException exception) {
            log.error("Exception : ", exception);
        }
    }
}
