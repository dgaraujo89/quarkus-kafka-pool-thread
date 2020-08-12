package org.acme;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@ApplicationScoped
public class MessageListener {

    private ThreadPoolExecutor executor;

    private static final int THREAD_POOL_SIZE = 10;

    public MessageListener() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
    }

    @Incoming("inbound")
    public void onMessage(String message) {
        while(executor.getActiveCount() == THREAD_POOL_SIZE) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }

        executor.submit(() -> {
            try {
                Thread.sleep(rand(0, 1000));
            } catch (InterruptedException e) {}

            System.out.println(message);
        });
    }

    private int rand(int min, int max) {
        var num = (int) (Math.random() * ((max - min) + 1)) + min;

        return num % 2 == 0 ? 200 : 2000;
    }
}
