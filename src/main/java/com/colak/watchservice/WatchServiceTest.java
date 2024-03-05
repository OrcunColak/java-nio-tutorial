package com.colak.watchservice;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WatchServiceTest {

    public static void main(String[] args) {
        Path watchPath = Paths.get(".");
        log.info("Watching the directory : {}", watchPath.toAbsolutePath());

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

            watchPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key = watchService.poll(2, TimeUnit.SECONDS);
            if (key != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("Event kind: {}. File affected:{}.", event.kind(), event.context());
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path filePath = watchPath.resolve((Path) event.context());
                        log.info("Found new entry : {}", filePath);
                    }
                }
                // If you forget to reset the key, it won't receive further events.
                // This is necessary if we are using in a loop
                key.reset();
            } else {
                log.info("No new entries found");
            }
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
