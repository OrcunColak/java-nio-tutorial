Process Large File  
1. Use Parallel Stream
```java
public void consumeFile() throws IOException {

  try (var bufferedReader = new BufferedReader(new FileReader("chat_logs.txt"))) {
    bufferedReader
        .lines()
        .parallel()
        .map(this::toDto)
        .filter(this::discardUnneeded)
        .map(this::anonymizeData)
        .forEach(this::sendToML);
  }
}
```
2. Use Flux to increare parallelism
```java
public void consumeFile() throws IOException {

  try (var bufferedReader = new BufferedReader(new FileReader("chat_logs.txt"))) {
    Flux.fromStream(bufferedReader.lines())
        .parallel(100)
        .runOn(Schedulers.boundedElastic()) // Creates more threads than the number of cores.
        .map(this::toDto)
        .filter(this::discardUnneeded)
        .map(this::anonymizeData)
        .doOnNext(this::sendToML)
        .sequential()
        .blockLast(); // Waits until all the data has been processed.

  }
}
```
