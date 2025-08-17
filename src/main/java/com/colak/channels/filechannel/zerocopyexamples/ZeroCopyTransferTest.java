package com.colak.channels.filechannel.zerocopyexamples;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// See https://medium.com/@neerupujari5/how-i-made-java-transfers-10x-faster-by-rethinking-architecture-1fb1ce761815
// Zero-copy transfer using NIO
// When using FileChannel transferTo() and transferFrom() methods, data doesn't pass through the Java heap,
// thus reducing garbage collection pressure. This immediately gave us a 3x performance boost.
public class ZeroCopyTransferTest {

    public long transferFile(String source, String destination) throws IOException {
        try (FileChannel sourceChannel = FileChannel.open(Paths.get(source), StandardOpenOption.READ);
             FileChannel destinationChannel = FileChannel.open(Paths.get(destination),
                     StandardOpenOption.CREATE,
                     StandardOpenOption.WRITE)) {

            long size = sourceChannel.size();
            // Direct kernel-to-kernel transfer
            return sourceChannel.transferTo(0, size, destinationChannel);
        }
    }
}
