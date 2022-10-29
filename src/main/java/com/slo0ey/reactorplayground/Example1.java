package com.slo0ey.reactorplayground;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class Example1 {
  private static final Logger LOGGER = Loggers.getLogger(Example1.class);

  public static void run()  {
    CountDownLatch latch = new CountDownLatch(1);

    Mono.just("Reactor Practice")
            .doOnSubscribe(sub -> LOGGER.info("Mono.just example"))
            .subscribe(LOGGER::info);

    Flux.just(1, 2, 3, 4, 5)
            .doOnSubscribe(sub -> LOGGER.info("Flux.just example"))
            .map(String::valueOf)
            .subscribe(LOGGER::info);

    Flux.interval(Duration.ofSeconds(1))
            .doOnSubscribe(sub -> LOGGER.info("Flux.interval example"))
            .take(10)
            .map(String::valueOf)
            .doOnComplete(latch::countDown)
            .subscribe(LOGGER::info);

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}