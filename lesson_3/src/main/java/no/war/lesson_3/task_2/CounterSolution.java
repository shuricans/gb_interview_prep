package no.war.lesson_3.task_2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterSolution {

    static class Counter {
        private final Lock lock = new ReentrantLock();

        private volatile int amount;

        public Counter(int initialValue) {
            this.amount = initialValue;
        }

        public void count() {
            lock.lock();
            try {
                amount++;
            } finally {
                lock.unlock();
            }
        }

        public int getAmount() {
            return amount;
        }
    }
}
