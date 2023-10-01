package utils;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

public class AppUtil {
    public static String getField(JsonNode json, String key, boolean required) {
        if (!json.hasNonNull(key)) {
            if (required) {
                throw new IllegalStateException(key + " can not be missing");
            } else {
                return null;
            }
        }
        return json.get(key).asText();
    }

    public static Integer getIntField(JsonNode json, String key, boolean required) {
        if (!json.hasNonNull(key)) {
            if (required) {
                throw new IllegalStateException(key + " can not be missing");
            } else {
                return null;
            }
        }
        return json.get(key).asInt();
    }

    public static JsonNode createMessageNode(String message) {
        return Json.newObject().put("message", message);
    }
}