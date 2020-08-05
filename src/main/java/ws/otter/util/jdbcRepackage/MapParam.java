package ws.otter.util.jdbcRepackage;

import java.util.HashMap;
import java.util.Map;

public class MapParam {

    private Map<String, Object> mp = new HashMap<String, Object>();

    public void addValue(String key, Object value) {
        this.mp.put(key, value);
    }

    public Object get(String key) {
        return this.mp.get(key);
    }
}