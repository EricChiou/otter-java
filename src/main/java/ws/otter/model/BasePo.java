package ws.otter.model;

import ws.otter.annotation.Table;

public class BasePo {

    public String tableName() {
        Class<?> c = this.getClass();
        if (c.isAnnotationPresent(Table.class)) {
            Table t = c.getAnnotation(Table.class);
            if (t != null) {
                if (t.name() != null && t.name().length() > 0) {
                    return t.name();
                }
            }
        }

        return c.toString();
    }

}