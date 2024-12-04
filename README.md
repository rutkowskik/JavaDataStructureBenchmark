# JavaDataStructureBenchmark
The objective of this thesis was to analyze data structures in Java, examining their impact on sequential and concurrent programs and comparing standard implementations with their functional counterparts. The work aimed to demonstrate the advantages of the functional approach over traditional procedural and object-oriented methodologies.

Persistent data structures in the problem of performance optimization of sequential and concurrent programs in Java
The master's thesis titled "Persistent Data Structures in the Problem of Optimizing the Performance of Sequential and Concurrent Programs in Java" focuses on analyzing various data structures available in the Java language and their impact on program performance. The author compares traditional implementations of data structures with their functional counterparts, emphasizing the benefits of the functional approach compared to procedural and object-oriented approaches.
The research in the thesis includes testing different data structures in both sequential and concurrent programs. The test results indicate that persistent data structures, such as CopyOnWriteArraySet, offer high performance in read operations in multi-threaded environments, comparable to or even better than standard structures. Moreover, structures like PMap and CopyOnWriteArraySet demonstrate good scalability and performance in multi-threaded environments and with large data sets.
The author concludes that selecting the appropriate data structure is crucial for optimizing application performance, particularly depending on the number of threads, the ratio of read operations, and the size of the processed data.

Result:
1. The results indicate that ArrayList outperforms CopyOnWriteArrayList with fewer threads and a lower percentage of read operations. However, CopyOnWriteArrayList significantly surpasses ArrayList with a higher number of threads and a greater proportion of read operations. These findings confirm that selecting the appropriate data structure depends on the specific requirements of the application, particularly the number of threads and the dominant operation types. 
<img width="1006" alt="image" src="https://github.com/user-attachments/assets/4958241d-997d-4857-aabc-410887fd03ec">

2. Depending on the application's needs, the choice between HashSet and CopyOnWriteArraySet depends on several factors, such as the number of threads, the percentage of read operations, and the size of the data. HashSet appears to be a better choice for applications requiring a high number of concurrent operations, especially with larger data sets, while CopyOnWriteArraySet may be more suitable for applications dominated by read operations with a smaller number of threads.
<img width="1006" alt="image" src="https://github.com/user-attachments/assets/b3f77337-ed7f-4be2-ab1c-95671fda0caf">

3. The research results indicate that PMap outperforms HashMap, especially with larger data sets and in multi-threaded environments. PMap is less sensitive to increases in the number of threads and the percentage of read operations, making it a more scalable and efficient solution. On the other hand, despite its optimizations, HashMap exhibits longer operation times, particularly with large data sets and a higher percentage of read operations, which may limit its applicability in more demanding scenarios.
<img width="1006" alt="image" src="https://github.com/user-attachments/assets/6e9c49e7-bdfd-4b22-9097-82ac1da700d1">

4. The analysis based on the results highlights the following observations:
- PMap
     - Low thread count (1 and 2): Maintains stable performance with relatively low operation times, regardless of the percentage of read operations.
     - High thread count (4): Exhibits a slight increase in operation time, but remains relatively stable compared to other implementations.
- ConcurrentHashMap
    - Low thread count (1 and 2): Displays greater variability in operation time, influenced by the percentage of read operations, with increasing operation time as thread count grows.
    - High thread count (4): Shows a noticeable increase in operation time, especially with a high percentage of read operations, likely due to increased synchronization overhead.
- CopyOnWriteArraySet
    - Low thread count (1 and 2): Achieves the lowest operation times under conditions of fewer threads and higher percentages of read operations.
    - High thread count (4): Experiences a significant increase in operation times due to the high cost of copying during modifications, particularly with lower percentages of read operations.
<img width="1006" alt="image" src="https://github.com/user-attachments/assets/7da2cbfd-9671-4b22-9d02-b68aeeb994ee">


