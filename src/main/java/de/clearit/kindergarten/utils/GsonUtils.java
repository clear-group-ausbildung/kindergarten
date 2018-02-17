package de.clearit.kindergarten.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class GsonUtils {

  private static final String CSN = GsonUtils.class.getSimpleName();
  private static final String METHOD_PREFIX = "{}::";
  private static final Logger LOGGER = LoggerFactory.getLogger(GsonUtils.class);

  private GsonUtils() {
    super();
  }

  public static JsonReader createJsonReaderFor(File file) throws IOException {
    LOGGER.debug(METHOD_PREFIX + "createJsonParserFor(File file) -> ({})", CSN, file);
    try (final JsonReader jsonReader = new JsonReader(new FileReader(file))) {
      LOGGER.debug("Initial parser");
      // jsonReader.nextToken(); // JsonToken.START_OBJECT
      LOGGER.debug("Parser after .nextToken()");
      // debugJsonParser(jsonReader);
      ha
      LOGGER.debug(METHOD_PREFIX + "createJsonParserFor(File file)", CSN);
      return jsonReader;
    }
  }

  /**
   * Handle an Object. Consume the first token which is BEGIN_OBJECT. Within the
   * Object there could be array or non array tokens. We write handler methods for
   * both. Noe the peek() method. It is used to find out the type of the next
   * token without actually consuming it.
   * 
   * @param reader
   * @throws IOException
   */
  private static void handleObject(JsonReader reader) throws IOException {
    reader.beginObject();
    while (reader.hasNext()) {
      JsonToken token = reader.peek();
      if (token.equals(JsonToken.BEGIN_ARRAY))
        handleArray(reader);
      else if (token.equals(JsonToken.END_OBJECT)) {
        reader.endObject();
        return;
      } else
        handleNonArrayToken(reader, token);
    }

  }

  /**
   * Handle a json array. The first token would be JsonToken.BEGIN_ARRAY. Arrays
   * may contain objects or primitives.
   * 
   * @param reader
   * @throws IOException
   */
  public static void handleArray(JsonReader reader) throws IOException {
    reader.beginArray();
    while (true) {
      JsonToken token = reader.peek();
      if (token.equals(JsonToken.END_ARRAY)) {
        reader.endArray();
        break;
      } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
        handleObject(reader);
      } else if (token.equals(JsonToken.END_OBJECT)) {
        reader.endObject();
      } else
        handleNonArrayToken(reader, token);
    }
  }

  /**
   * Handle non array non object tokens
   * 
   * @param reader
   * @param token
   * @throws IOException
   */
  public static void handleNonArrayToken(JsonReader reader, JsonToken token) throws IOException {
    if (token.equals(JsonToken.NAME))
      System.out.println(reader.nextName());
    else if (token.equals(JsonToken.STRING))
      System.out.println(reader.nextString());
    else if (token.equals(JsonToken.NUMBER))
      System.out.println(reader.nextDouble());
    else
      reader.skipValue();
  }

}
