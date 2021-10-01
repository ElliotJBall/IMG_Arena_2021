package com.imgarena.coding.challenge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Change me.
 *
 * @author Elliot Ball
 */
public class TestUtils {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(
      new JavaTimeModule());

  public static JsonNode loadJsonFile(final String path) throws IOException, URISyntaxException {
    return OBJECT_MAPPER.readTree(
        Files.readString(Paths.get(
            Objects.requireNonNull(TestUtils.class.getClassLoader().getResource(path)).toURI())));
  }
}
