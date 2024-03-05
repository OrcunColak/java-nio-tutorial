package com.colak.channels.serversocketchannel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * See <a href="https://medium.com/@lfoster49203/advanced-java-i-o-delving-deep-into-nio-nio2-asynchronous-channels-and-sockets-87c291441ce7">...</a>
 */
@Slf4j
public class ServerSocketChannelTest {

    public static void main(String[] args) {

        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocket = ServerSocketChannel.open()) {

            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select(); // Blocks until there's an I/O event
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isAcceptable()) {
                        SocketChannel client = serverSocket.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        client.read(buffer);
                        log.info("Received: " + new String(buffer.array()).trim());
                    }
                }
                selector.selectedKeys().clear(); // Clear the selected keys
            }
        } catch (IOException exception) {
            log.error("Exception : ", exception);
        }

    }
}
