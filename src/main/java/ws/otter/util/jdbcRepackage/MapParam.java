package ws.otter.util.jdbcRepackage;

import java.util.HashMap;
import java.util.Map;

public class MapParam {

    private Map<String, String> mp = new HashMap<String, String>();

    public void addValue(String key, String value) {
        this.mp.put(key, value);
    }

    public String get(String key) {
        return this.mp.get(key);
    }
}