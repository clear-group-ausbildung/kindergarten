package de.clearit.kindergarten.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;

/**
 * Inspired by <a href=
 * "http://www.nurkiewicz.com/2017/09/streaming-large-json-file-with-jackson.html">http://www.nurkiewicz.com/2017/09/streaming-large-json-file-with-jackson.html</a>
 * <br>
 * Excerpt of {@code export.json}:
 * 
 * <pre>
 * [{"itemNumber":1,"itemPrice":1.0,"vendorNumber":71},{"itemNumber":1,"itemPrice":15.0,"vendorNumber":72}]
 * </pre>
 * 
 * Broken into JsonTokens:
 * 
 * <pre>
 * START_ARRAY         '['
 * START_OBJECT        '{'
 * FIELD_NAME          'itemNumber'
 * VALUE_NUMBER_INT    '1'
 * FIELD_NAME          'itemNumber'
 * VALUE_NUMBER_DOUBLE '1.0'
 * FIELD_NAME          'vendorNumber'
 * VALUE_NUMBER_INT    '71'
 * END_OBJECT          '}'
 * START_OBJECT        '{'
 * FIELD_NAME          'itemNumber'
 * VALUE_NUMBER_INT    '1'
 * FIELD_NAME          'itemNumber'
 * VALUE_NUMBER_DOUBLE '15.0'
 * FIELD_NAME          'vendorNumber'
 * VALUE_NUMBER_INT    '72'
 * END_OBJECT          '}'
 * END_ARRAY           ']'
 * </pre>
 */
public class JacksonUtils {

  private static final Logger LOGGER = Logger.getLogger(JacksonUtils.class.getName());

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

  public static JsonParser createJsonParserFor(File file) throws IOException {
    LOGGER.entering(JacksonUtils.class.getSimpleName(), "createJsonParserFor(File file)");
    try (final JsonParser jsonParser = new JsonFactory().createJsonParser(file)) {
      LOGGER.fine("Initial parser");
      debugJsonParser(jsonParser);
      jsonParser.nextToken(); // JsonToken.START_OBJECT
      LOGGER.fine("Parser after .nextToken()");
      debugJsonParser(jsonParser);
      LOGGER.exiting(JacksonUtils.class.getSimpleName(), "createJsonParserFor(File file)");
      return jsonParser;
    }
  }

  public static void debugJsonParser(JsonParser parser) throws IOException {
    if (parser.getCurrentName() == null) {
      LOGGER.fine("parser.getCurrentName(): <null>");
    } else {
      LOGGER.fine("parser.getCurrentName(): " + parser.getCurrentName());
    }
    if (parser.getCurrentToken() == null) {
      LOGGER.fine("parser.getCurrentToken(): <null>");
    } else {
      LOGGER.fine("parser.getCurrentToken(): " + parser.getCurrentToken());
    }
    if (parser.nextToken() == null) {
      LOGGER.fine("parser.nextToken(): <null>");
    } else {
      LOGGER.fine("parser.nextToken(): " + parser.nextToken());
    }
    if (parser.nextValue() == null) {
      LOGGER.fine("parser.nextValue(): <null>");
    } else {
      LOGGER.fine("parser.nextValue(): " + parser.nextValue());
    }
  }

}
