package com.genologics.statify;

import com.genologics.statify.loggers.DefaultStatifyLogger;
import com.genologics.statify.loggers.StatifyLogger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Jordens
 */
public class Statify {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final String guid;
    private StatifyLogger logger = new DefaultStatifyLogger();

    public Statify(String hostname) {
        this("generated-guid", hostname);
    }

    Statify(String guid, String hostname) {
        this.guid = guid;
    }

    public void log(String key, Object value) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("guid", guid);
        data.put("key", key);
        data.put("value", value);

        try {
            String json = OBJECT_MAPPER.writeValueAsString(data);
            logger.info("Logging: " + json);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
