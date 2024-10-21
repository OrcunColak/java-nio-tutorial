package com.colak.files.lines;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
        URL url = FilesLinesTest.class.getClassLoader().getResource("test.txt");
        URI uri = Objects.requireNonNull(url).toURI();
        Path file = Paths.get(uri);

        printFileExample(file);
        findLineExample(file);
    }

    private static void printFileExample(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file)) {
            lines.forEach(log::info);
        }
    }

    private static void findLineExample(Path file) throws IOException {
        Optional<String> line;
        try (Stream<String> lines = Files.lines(file)) {
            line = lines.filter(l -> l.contains("line2"))
                    .findFirst();
        }
        log.info("Line : {}", line);
    }
}
