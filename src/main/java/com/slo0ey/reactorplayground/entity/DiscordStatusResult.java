package com.slo0ey.reactorplayground.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public final class DiscordStatusResult {
  private final JsonNode page;
  private final Status status;

  @JsonCreator
  public DiscordStatusResult(@JsonProperty("page") JsonNode page, @JsonProperty("status") Status status) {
    this.page = page;
    this.status = status;
  }

  public Status status() {
    return status;
  }

  public static final class Status {
    private final String indicator;
    private final String description;

    @JsonCreator
    public Status(
            @JsonProperty("indicator") String indicator,
            @JsonProperty("description") String description) {
      this.indicator = indicator;
      this.description = description;
    }

    public String indicator() {
      return indicator;
    }

    public String description() {
      return description;
    }

    @Override
    public String toString() {
      return "{ indicator=" + indicator + ", description=" + description + "}";
    }
  }

}

