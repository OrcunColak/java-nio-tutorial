package com.colak.files;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
class FilesLinesTest {

    public static void main() throws Exception {
        URI uri = FilesLinesTest.class.getClassLoader().getResource("test.txt").toURI();
        Path file = Paths.get(uri);

        Optional<String> line;
        try (Stream<String> lines = Files.lines(file)) {
            line = lines.filter(l -> l.contains("line2"))
                    .findFirst();
        }
        log.info("Line : {}", line);
    }
}
