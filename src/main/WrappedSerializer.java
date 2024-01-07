package main;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class WrappedSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Your dynamic logic here to determine whether to include or exclude the field
        // For simplicity, I'm assuming a boolean flag named 'includeField'

        boolean includeField = (boolean)value;

        if (includeField) {
            if (value instanceof Map) {
                // Serialize Map
                gen.writeObject(value);
            } else if (value instanceof String) {
                // Serialize String
                gen.writeString((String) value);
            }
        }
        // If not included, do nothing (field will be ignored)
    }
}