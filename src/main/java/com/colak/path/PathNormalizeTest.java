package com.colak.path;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@UtilityClass
public class PathNormalizeTest {

    public static void main() throws IOException {
        String uploadDir = "./upload-directory";
        Path directory = Paths.get(uploadDir);

        String fileName = "my-file.txt";
        Path filePath = directory.resolve(fileName);
        // .\\upload-directory\my-file.txt
        log.info("Resolve path: {}", filePath);

        // Eliminate redundant name elements such as "." and ".."
        Path normalizedPath = filePath.normalize();
        // upload-directory\my-file.txt
        log.info("Normalized path: {}", normalizedPath);
    }
}
