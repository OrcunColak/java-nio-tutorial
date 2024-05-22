package com.colak.files;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@UtilityClass
class FilesWriteStringTouchTest {

    public static void main() throws IOException {
        // Create example.txt at project root folder
        Path path = Paths.get("example.txt");
        String content = "value";
        touch(path, content);

        String readContent = Files.readString(path);
        log.info("readContent : {}", readContent);
    }

    private static void touch(Path path, String content) throws IOException {
        // Same as Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        // Same as FileChannel.open(file.toPath(), StandardOpenOption.WRITE).truncate(0).close();
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}
