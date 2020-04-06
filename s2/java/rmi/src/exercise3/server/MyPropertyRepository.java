package exercise3.server;

import java.util.HashMap;
import java.util.Map;

import exercise3.PropertyRepository;

public class MyPropertyRepository implements PropertyRepository {
    private Map<String,String> map;

    public MyPropertyRepository() {
        map = new HashMap<>();
    }

    @Override
    public String getProperty(String key) {
        return map.get(key);
    }

    @Override
    public void setProperty(String key, String value) {
        map.put(key, value);
    }

    @Override
    public void removeProperty(String key) {
        map.remove(key);
    }
}
