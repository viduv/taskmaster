package fr.fpage.backend.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets RestartType
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-02-02T17:17:43.346542200+01:00[Europe/Paris]")
public enum RestartType {
  
  ALWAYS("ALWAYS"),
  
  NEVER("NEVER"),
  
  ON_FAILURE("ON_FAILURE");

  private String value;

  RestartType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static RestartType fromValue(String value) {
    for (RestartType b : RestartType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

