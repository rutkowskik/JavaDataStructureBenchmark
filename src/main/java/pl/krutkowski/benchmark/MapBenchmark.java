package org.example.benchmark;

import org.openjdk.jmh.annotations.*;
import org.pcollections.PMap;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2)
@Fork(1)
public class MapBenchmark {

    @Param({"1", "2", "4"}) // number of threads
    private int threads;
    @Param({"50", "60", "70", "80","90","100"}) // percentage of read operations
    private int readPercentage;
    int[] readToWriteRatios = {50, 60, 70, 80, 90, 100};
    private Map<TestModel, Integer> hashMap;
    PMap<TestModel, Integer> pMap;
    List<Operation> ops;
    @Param({"10000","100000"})
    int OPERATIONS;

    @Setup(Level.Invocation)
    public void setup() {

        HashMap<TestModel, Integer> original = new HashMap<>(OPERATIONS * 2);
        for (int i = 0; i < OPERATIONS * 2; i++) {
            original.put(new TestModel("Name", "LastName", 100001 + i), 100001 + i);
        }

        // Half of the values go into the initial list
        Map<TestModel, Integer> target = new HashMap<>(OPERATIONS);
        for (int i = 0; i < OPERATIONS; i++) {
            TestModel key = new TestModel("Name", "LastName", 100001 + i);
            Integer value = original.get(key);
            target.put(key, value);
        }

        for (int ratio : readToWriteRatios) {

            int operations = OPERATIONS; // Number of operations to perform
            int reads = operations * ratio / 100;
            int writes = operations - reads;
            ops = new ArrayList<>(operations);

            int removeCount = writes / 2;
            int addCount = writes - removeCount;

            // Remove operations
            for (int i = 0; i < removeCount; ++i) {
                TestModel key = new TestModel("Name", "LastName", 100001 + i);
                ops.add(new Operation(Type.REMOVE, key));
            }

            // Add operations
            for (int i = 0; i < addCount; ++i) {
                TestModel key = new TestModel("Name", "LastName", 100001 + i);
                ops.add(new Operation(Type.ADD, key));
            }

            Collections.shuffle(ops);  // Operations will be executed in random order

            // Add synchronization to HashMap
            hashMap = Collections.synchronizedMap(new HashMap<>(target));

        }
    }

    @Benchmark
    public void benchmarkArrayList() throws ExecutionException, InterruptedException {
        testMapPerformance(hashMap, ops, threads);
    }

    @Benchmark
    public void benchmarkCopyOnWriteArrayList() throws ExecutionException, InterruptedException {
        testMapPerformance(pMap, ops, threads);
    }

    private static void testMapPerformance(Map<TestModel, Integer> map, List<Operation> ops, int numberOfThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        LongAdder readSuccess = new LongAdder();
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;

            executor.execute(() -> {
                for (int j = threadId; j < ops.size(); j += numberOfThreads) {
                    var op = ops.get(j);
                    if (op.type_ == Type.READ) {
                        if (map.containsKey(op.value_)) {
                            readSuccess.increment();
                        }
                    } else if (op.type_ == Type.ADD) {
                        map.put(op.value_, op.value_.getPesel());
                    } else if (op.type_ == Type.REMOVE) {
                        map.remove(op.value_);
                    }
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
