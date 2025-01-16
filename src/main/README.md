# Channel
There are several types of channels, each designed for a specific purpose. These include:

- FileChannel: used for reading and writing files
- SocketChannel: used for TCP network communication
- ServerSocketChannel: used for accepting incoming TCP connections
- DatagramChannel: used for UDP network communication

# Process Large File  
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
