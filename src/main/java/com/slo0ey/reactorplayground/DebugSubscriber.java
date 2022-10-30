package com.slo0ey.reactorplayground;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.util.Logger;
import reactor.util.Loggers;

public class DebugSubscriber<T> extends BaseSubscriber<T> {
  private static final Logger LOGGER = Loggers.getLogger(DebugSubscriber.class);

  @Override
  protected void hookOnSubscribe(Subscription subscription) {
    request(Long.MAX_VALUE);
    LOGGER.info(" | onSubscribe");
  }

  @Override
  protected void hookOnNext(T value) {
    LOGGER.info(" | onNext({})", value);
  }

  @Override
  protected void hookOnError(Throwable throwable) {
    LOGGER.error(" | onError({})", throwable);
  }

  @Override
  protected void hookOnCancel() {
    LOGGER.info(" | onCancel");
  }

  @Override
  protected void hookOnComplete() {
    LOGGER.info(" | onComplete");
  }
}
