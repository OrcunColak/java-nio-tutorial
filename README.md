See https://medium.com/@neerupujari5/how-i-made-java-transfers-10x-faster-by-rethinking-architecture-1fb1ce761815

Performance Results (1GB file processing):

Traditional I/O: 2,300ms
Zero-copy transfer: 800ms (3.9x faster)
Memory-mapped: 400ms (5.8x faster)
Async + Virtual Threads: 230ms (10x faster)

There are 3 options for file processing
1. Using FileChannel 
2. Using MappedByteBuffer 
3. Using AsynchronousFileChannel with threads