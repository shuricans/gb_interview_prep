package no.war.lesson_3.task_2;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static no.war.lesson_3.task_2.CounterSolution.Counter;
import static org.assertj.core.api.Assertions.assertThat;

class CounterSolutionTest {

    @Test
    public void counterTest() throws InterruptedException {
        // given
        Counter counter = new Counter(0);
        AtomicInteger atomicInteger = new AtomicInteger(0);

        final int amountThreads = 5;
        final int amountCyclesPerThread = 1000;
        final int expectedCounterValue = amountThreads * amountCyclesPerThread;

        CountDownLatch latch = new CountDownLatch(amountThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(amountThreads);

        // when
        for (int i = 0; i < amountThreads; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < amountCyclesPerThread; j++) {
                    counter.count();
                    atomicInteger.incrementAndGet();
                }
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        assertThat(atomicInteger.get())
                .isEqualTo(expectedCounterValue)
                .isEqualTo(counter.getAmount());
    }
}