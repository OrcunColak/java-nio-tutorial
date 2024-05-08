package com.colak.files;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@UtilityClass
class FilesWriteStringTempFileTest {

    public static void main() throws IOException {
        String content = "value";
        Path path = writeToTempFile(content);
        log.info("Path : {}", path);

        String readContent = Files.readString(path);
        log.info("readContent : {}", readContent);
    }

    private static Path writeToTempFile(String content) throws IOException {
        File temp = File.createTempFile("test", ".tmp");
        temp.deleteOnExit();
        Files.writeString(temp.toPath(), content, StandardCharsets.UTF_8);
        return temp.toPath();
    }
}
