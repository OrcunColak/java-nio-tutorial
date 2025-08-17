package com.colak.channels.asynchronousfilechannel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

// Asynchronous file processing pipeline
@Slf4j
public class AsyncFileProcessor {
    private final ExecutorService ioExecutor =
            Executors.newVirtualThreadPerTaskExecutor(); // Java 21+

    public class ProcessingResult {
    }

    public class FileResult {
    }

    public CompletableFuture<ProcessingResult> processAsync(List<String> filePaths) {

        return CompletableFuture.supplyAsync(() -> {
                    return filePaths.parallelStream()
                            .map(this::processFileAsync)
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());
                }, ioExecutor)
                .thenApply(this::aggregateResults);
    }

    private ProcessingResult aggregateResults(List<FileResult> fileResults) {
        return null;
    }

    private CompletableFuture<FileResult> processFileAsync(String path) {
        return CompletableFuture.supplyAsync(() -> {
            try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(path))) {

                ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024);
                Future<Integer> result = channel.read(buffer, 0);

                // Non-blocking processing continues here
                return processBuffer(buffer, result.get());
            } catch (IOException | InterruptedException | ExecutionException exception) {
                log.error("Exception : ", exception);
            }
            return null;
        }, ioExecutor);
    }

    private FileResult processBuffer(ByteBuffer buffer, Integer integer) {
        return new FileResult();
    }
}
