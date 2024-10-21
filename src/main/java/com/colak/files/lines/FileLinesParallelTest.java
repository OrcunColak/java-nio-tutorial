package com.colak.files.lines;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// This is an example to process 10 GB file
// See https://dip-mazumder.medium.com/processing-a-large-log-file-of-10gb-using-java-parallel-streams-693bb98828aa
@Slf4j
public class FileLinesParallelTest {

    public static void main() throws Exception {
        // Path to the large 10GB transaction log file
        String filePath = "path/to/transaction_log.txt";

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            // Process the file in parallel, filter and sum the transactions greater than 10000
            double total = lines
                    .parallel() // Convert to parallel stream
                    .map(FileLinesParallelTest::parseTransactionAmount)
                    .filter(amount -> amount > 10000) // Filter transactions greater than 10000
                    .mapToDouble(Double::doubleValue) // Convert Stream<Double> to DoubleStream
                    .sum(); // Sum the filtered amounts

            log.info("Total sum of transactions greater than 10,000: {}", total);
        }
    }

    // Helper method to parse transaction amount from a line (assuming a specific format)
    private static Double parseTransactionAmount(String line) {
        String[] fields = line.split(",");
        return Double.parseDouble(fields[1]); // Extract the amount
    }
}
