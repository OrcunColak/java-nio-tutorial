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
                for (SelectionKey selectionKey : selector.selectedKeys()) {
                    if (selectionKey.isAcceptable()) {
                        acceptServerSocket(serverSocket, selector);

                    } else if (selectionKey.isReadable()) {
                        readSelectionKey(selectionKey, readBuffer);
                    }
                }
                // Instead of using an iterator we are using for loop.
                // So clear the selected keys
                selector.selectedKeys().clear();
            }
        } catch (IOException exception) {
            log.error("Exception : ", exception);
        }

    }

    private static void acceptServerSocket(ServerSocketChannel serverSocket, Selector selector) throws IOException {
        SocketChannel clientChannel = serverSocket.accept();
        clientChannel.configureBlocking(false);

        // See https://medium.com/@hnasr/this-may-improve-your-backend-tcp-responses-latency-5f25ebe53fc0
        // Causes the kernel to send whatever it has in the send buffer even if it's a few bytes.
        clientChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);

        clientChannel.register(selector, SelectionKey.OP_READ);
        log.info("Connection Accepted: {}", clientChannel.getRemoteAddress());
    }

    private static void readSelectionKey(SelectionKey selectionKey, ByteBuffer readBuffer) throws IOException {
        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();

        int bytesRead = clientChannel.read(readBuffer);
        if (bytesRead == -1) {
            // Client closed the connection
            selectionKey.cancel();
            log.info("Client disconnected: {}", clientChannel.getRemoteAddress());
            clientChannel.close();
        } else {
            readBuffer.flip();

            String message = new String(readBuffer.array(), 0, readBuffer.remaining());
            log.info("Received: {}", message);
            readBuffer.clear();

            String echo = "Received:" + message;
            ByteBuffer echoBuffer = ByteBuffer.wrap(echo.getBytes());
            clientChannel.write(echoBuffer);
        }
    }


}
