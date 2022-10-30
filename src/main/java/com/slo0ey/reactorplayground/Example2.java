package com.slo0ey.reactorplayground;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slo0ey.reactorplayground.entity.DiscordStatusResult;
import com.slo0ey.reactorplayground.exception.HttpResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.Logger;
import reactor.util.Loggers;

public class Example2 {
  private static final Logger LOGGER = Loggers.getLogger(Example2.class);

  public static Mono<Void> run() {
    return Mono.fromRunnable(() -> {
      LOGGER.info("#########################################################################################");
      LOGGER.info("###################################### Example2 #########################################");
      LOGGER.info("#########################################################################################\n");

      LOGGER.info("### Api 호출 & 결과값 파싱 테스트 ###");
      HttpClient.create()
              .baseUrl("https://discordstatus.com/api/v2")
              .headers(header -> {
                header.add("Content-Type", "*/*");
              })
              .secure()
              .get()
              .uri("/status.json")
              .responseSingle((httpClientResponse, byteBufMono) -> {
                var code = httpClientResponse.status().code();
                if(code / 100 != 2) { // 2xx가 아닐 시
                  return Mono.error(new HttpResponseException(String.valueOf(code)));
                }
                return byteBufMono.asString();
              })
              .retry(1) // error 시 최대 1번 재시도
                      .flatMap(body -> Mono.fromSupplier(ObjectMapper::new)
                              .map(mapper -> {
                                try {
                                  return mapper.readValue(body, DiscordStatusResult.class);
                                } catch (JsonProcessingException e) {
                                  throw new RuntimeException(e);
                                }
                              })
                              .retry(1))
                              .map(DiscordStatusResult::status)
                      .doOnError(__ -> LOGGER.info("Failed to read value"))
                      .doOnSuccess(status -> LOGGER.info(status.toString()))
              .then()
              .block();
    });
  }
}
