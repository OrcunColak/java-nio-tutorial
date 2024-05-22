package com.colak.files;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
class FilesLinesTest {

    public static void main() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(FilesLinesTest.class.getClassLoader().getResource("test.txt")).toURI();
        Path file = Paths.get(uri);

        printFile(file);
        findLine(file);
    }

    private static void printFile(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file)) {
            lines.forEach(log::info);
        }
    }

    private static void findLine(Path file) throws IOException {
        Optional<String> line;
        try (Stream<String> lines = Files.lines(file)) {
            line = lines.filter(l -> l.contains("line2"))
                    .findFirst();
        }
        log.info("Line : {}", line);
    }
}
