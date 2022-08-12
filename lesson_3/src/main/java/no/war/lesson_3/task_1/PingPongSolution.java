package no.war.lesson_3.task_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PingPongSolution {

    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        PingPong ping = new PingPong("ping", lock, condition);
        PingPong pong = new PingPong("pong", lock, condition);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(ping);
        executorService.submit(pong);
        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }

    static class PingPong implements Runnable {

        private final Lock lock;
        private final String pingOrPong;
        private final Condition condition;

        PingPong(String pingOrPong, Lock lock, Condition condition) {
            this.lock = lock;
            this.pingOrPong = pingOrPong;
            this.condition = condition;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    // sleep 1 second, just for smooth
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(pingOrPong);
                    condition.signal();
                    condition.await();
                } catch (InterruptedException e) {
                    break;
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
