package com.colak.channels.serversocketchannel;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


// See https://medium.com/@lfoster49203/advanced-java-i-o-delving-deep-into-nio-nio2-asynchronous-channels-and-sockets-87c291441ce7

@Slf4j
@UtilityClass
public class ServerSocketChannelTest {

    public static void main() {

        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocket = ServerSocketChannel.open()) {

            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer readBuffer = ByteBuffer.allocate(256);

            while (true) {
                // Blocks until there's an I/O event
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }

                // iterate over the selected keys
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isAcceptable()) {
                        SocketChannel clientChannel = serverSocket.accept();
                        clientChannel.configureBlocking(false);

                        // See https://medium.com/@hnasr/this-may-improve-your-backend-tcp-responses-latency-5f25ebe53fc0
                        // Causes the kernel to send whatever it has in the send buffer even if it's a few bytes.
                        clientChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);

                        clientChannel.register(selector, SelectionKey.OP_READ);
                        log.info("Connection Accepted: {}", clientChannel.getRemoteAddress());

                    } else if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();

                        int read = clientChannel.read(readBuffer);
                        if (read == -1) {
                            // Client closed the connection
                            key.cancel();
                            log.info("Client disconnected: {}", clientChannel.getRemoteAddress());
                            clientChannel.close();
                        } else {
                            readBuffer.flip();

                            String string = new String(readBuffer.array(), 0, readBuffer.remaining());
                            log.info("Received: {}", string);
                            readBuffer.clear();

                            String ack = "Received:" + string;
                            ByteBuffer ackBuffer = ByteBuffer.wrap(ack.getBytes());
                            clientChannel.write(ackBuffer);
                        }
                    }
                }
                // Clear the selected keys
                selector.selectedKeys().clear();
            }
        } catch (IOException exception) {
            log.error("Exception : ", exception);
        }

    }
}
