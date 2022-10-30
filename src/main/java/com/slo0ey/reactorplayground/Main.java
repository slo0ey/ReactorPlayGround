package com.slo0ey.reactorplayground;

import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

public class Main {
  private static final Logger LOGGER = Loggers.getLogger(Main.class);

  public static void main(String[] args) {
    final Mono<Void> jobs = Mono.when(
      Example1.run(),
      Example2.run()
    );

    jobs.block();
  }
}
