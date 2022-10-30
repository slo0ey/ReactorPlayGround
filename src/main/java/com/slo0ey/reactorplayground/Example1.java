package com.slo0ey.reactorplayground;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.time.Duration;

public class Example1 {
  private static final Logger LOGGER = Loggers.getLogger(Example1.class);

  public static Mono<Void> run()  {
    return Mono.fromRunnable(() -> {
      LOGGER.info("#########################################################################################");
      LOGGER.info("###################################### Example1 #########################################");
      LOGGER.info("#########################################################################################\n");

      LOGGER.info("### Mono.just 테스트 ###");
      Mono.just("Reactor Practice")
              .log()
              .block();

      LOGGER.info("### Flux.just 테스트 ###");
      Flux.just(1, 2, 3, 4, 5)
              .log()
              .map(String::valueOf)
              .blockLast();

      LOGGER.info("### Flux.interval 테스트 ###");
      Flux.interval(Duration.ofSeconds(1))
              .log()
              .take(3) // complete 신호가 아닌 cancel 신호로 종료시킨다.
              .map(String::valueOf)
              .blockLast();
    });
  }
}