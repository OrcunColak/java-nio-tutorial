package com.colak.files;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

@UtilityClass
class FilesTouchTest {

    public static void main() {
        File file = new File("touchMe");
        touch(file);
    }

    private static void touch(File file) {
        Path path = file.toPath();
        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            Files.setLastModifiedTime(path, FileTime.from(Instant.now()));
        } catch (IOException exception) {
            throw new RuntimeException("Could not touch file " + file.getAbsolutePath(), exception);
        }
    }
}
