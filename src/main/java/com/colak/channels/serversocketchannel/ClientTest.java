package com.colak.channels.serversocketchannel;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

// See https://medium.com/@lfoster49203/advanced-java-i-o-delving-deep-into-nio-nio2-asynchronous-channels-and-sockets-87c291441ce7

@Slf4j
@UtilityClass
public class ClientTest {

    public static void main() throws Exception {
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8080);

        SocketChannel socketClient = SocketChannel.open(socketAddress);

        log.info("Connecting to Server on port 8080 ....");

        ArrayList<String> messages = new ArrayList<>();
        messages.add("Message 1");
        messages.add("Message 2");
        messages.add("Message 3");

        for (String message : messages) {

            byte[] bytes = message.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            socketClient.write(buffer);
            log.info("Sending: {}", message);
            buffer.clear();

            ByteBuffer ackBuffer = ByteBuffer.allocate(256);
            socketClient.read(ackBuffer);
            String ackRes = new String(ackBuffer.array()).trim();

            log.info("Ack from Server : {}", ackRes);
            Thread.sleep(2000);
        }
        socketClient.close();
    }
}
