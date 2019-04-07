package z.learn.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigService {
    private static final String CONFIG_BASE_DIR = "/CONFIGS/";
    private static final String _INITIALIZED = "_initialized";

    public Map<String, String> getConfig(String key) {
        HashMap result = new HashMap<>();
        result.put("hello", "world");
        return result;
    }

    public Map<String, String> setConfig(String key, String value) {

        return new HashMap<>();
    }

    public Map<String, String> deleteConfig(String key) {

        return new HashMap<>();
    }
}
