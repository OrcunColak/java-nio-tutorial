package com.colak.channels.filechannel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class FileChannelScatterReadTest {

    public static void main(String[] args) {

      ByteBuffer header = ByteBuffer.allocate(10); 
      ByteBuffer body = ByteBuffer.allocate(1024); 
      ByteBuffer footer = ByteBuffer.allocate(10); 
      ByteBuffer[] buffers = { header, body, footer };
      
      try (FileChannel channel = new FileInputStream("example.txt").getChannel(); ) {
        channel.read(buffers);
          
      } catch (IOException exception) {
        log.error("Exception : ", exception);
      }
    }
}
