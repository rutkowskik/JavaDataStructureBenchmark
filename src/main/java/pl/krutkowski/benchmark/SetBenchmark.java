package org.example.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

enum Type { ADD, READ, REMOVE; }

class Operation {

    Type type_;
    TestModel value_;

    public Operation(Type t, TestModel value) {
        type_ = t;
        value_ = value;
    }
}

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2)
@Fork(1)
public class SetBenchmark {

    @Param({"1", "2", "4"}) // number of threads
    private int threads;
    @Param({"50", "60", "70", "80","90","100"}) // percentage of read operations
    private int readPercentage;
    int[] readToWriteRatios = {50, 60, 70, 80, 90, 100};
    private Set<TestModel> hashSet;
    private Set<TestModel> copyOnWriteArraySet;
    List<Operation> ops;
    @Param({"10000","100000"})
    int OPERATIONS;

    @Setup(Level.Invocation)
    public void setup() {

        List<TestModel> range = new ArrayList<>(OPERATIONS * 2);
        for (int i = 0; i < OPERATIONS * 2; i++) {
            range.add(new TestModel("Name", "LastName", 100001 + i));
        }
        List<TestModel> original = new ArrayList<>(range);
        Collections.shuffle(original);  // random order

        // Half of the values go into the initial list
        List<TestModel> target = new ArrayList<>(OPERATIONS);
        for (int i = 0; i < OPERATIONS; i++) {
            target.add(original.get(i));
        }

        for (int ratio : readToWriteRatios) {

            int operations = OPERATIONS; // Number of operations to perform
            int reads = operations * ratio / 100;
            int writes = operations - reads;
            ops = new ArrayList<>(operations);

            var rng = ThreadLocalRandom.current();
            for (int i = 0; i < reads; ++i) {
                // 50% of the time, choose a value that exists, and 50% a value that doesn't exist
                TestModel value = rng.nextBoolean()
                        ? original.get(rng.nextInt(OPERATIONS))
                        : original.get(rng.nextInt(OPERATIONS, 2 * OPERATIONS));

                ops.add(new Operation(Type.READ, value));
            }

            int removeCount = writes / 2;
            int addCount = writes - removeCount;

            // Remove operations are only for values that are already in the list
            List<TestModel> temp = new ArrayList<>(target);
            Collections.shuffle(temp);  // random order
            for (int i = 0; i < removeCount; ++i) {
                ops.add(new Operation(Type.REMOVE, temp.get(i)));
            }

            // Add operations are 50% for elements already in the initial list and 50% for new ones
            List<TestModel> toAdd = new ArrayList<>(target);
            Collections.shuffle(toAdd);
            for (int i = 0; i < addCount; ++i) {
                ops.add(new Operation(Type.ADD, toAdd.get(i)));
            }

            Collections.shuffle(ops);  // Operations will be executed in random order

            hashSet = Collections.synchronizedSet(new HashSet<>(target));
            copyOnWriteArraySet = new CopyOnWriteArraySet<>(target);
        }
    }

    @Benchmark
    public void benchmarkArrayList() throws ExecutionException, InterruptedException {
        testSetPerformance(hashSet, ops, threads);
    }

    @Benchmark
    public void benchmarkCopyOnWriteArrayList() throws ExecutionException, InterruptedException {
        testSetPerformance(copyOnWriteArraySet, ops, threads);
    }

    private static void testSetPerformance(Set<TestModel> set, List<Operation> ops, int numberOfThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        LongAdder readSuccess = new LongAdder();
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;

            executor.execute(() -> {
                for (int j = threadId; j < ops.size(); j += numberOfThreads) {
                    var op = ops.get(j);
                    if (op.type_ == Type.READ) {
                        if (set.contains(op.value_)) {
                            readSuccess.increment();
                        }
                    } else if (op.type_ == Type.ADD) {
                        set.add(op.value_);
                    } else if (op.type_ == Type.REMOVE) {
                        set.remove(op.value_);
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
