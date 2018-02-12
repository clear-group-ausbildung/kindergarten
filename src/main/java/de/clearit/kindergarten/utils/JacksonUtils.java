package de.clearit.kindergarten.utils;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;

public class JacksonUtils {

  private JacksonUtils() {
    super();
  }

  public static int readInt(JsonParser parser) throws IOException {
    parser.nextValue();
    return parser.getIntValue();
  }

  public static double readDouble(JsonParser parser) throws IOException {
    parser.nextValue();
    return parser.getDoubleValue();
  }

  public static JsonParser parser(File file) throws IOException {
    try (final JsonParser parser = new JsonFactory().createJsonParser(file)) {
      // parser.nextToken();
      debugParser(parser);
      // while (!parser.isClosed() && parser.nextToken() != null &&
      // !JsonToken.END_OBJECT.equals(parser.nextToken())) {
      // debugParser(parser);
      // return parser;
      // }
      return parser;
    }
  }

  public static void debugParser(JsonParser parser) throws IOException {
    if (parser.getCurrentName() == null) {
      System.err.println("parser.getCurrentName(): <null>");
    } else {
      System.err.println("parser.getCurrentName(): " + parser.getCurrentName());
    }
    if (parser.getCurrentToken() == null) {
      System.err.println("parser.getCurrentToken(): <null>");
    } else {
      System.err.println("parser.getCurrentToken(): " + parser.getCurrentToken());
    }
    if (parser.nextToken() == null) {
      System.err.println("parser.nextToken(): <null>");
    } else {
      System.err.println("parser.nextToken(): " + parser.nextToken());
    }
    if (parser.nextValue() == null) {
      System.err.println("parser.nextValue(): <null>");
    } else {
      System.err.println("parser.nextValue(): " + parser.nextValue());
    }
  }

}
