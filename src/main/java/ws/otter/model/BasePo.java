package ws.otter.model;

import java.lang.reflect.Field;

import ws.otter.annotation.Table;

public class BasePo {

    /** Get table name */
    public String table() {
        final Class<?> c = this.getClass();
        if (c.isAnnotationPresent(Table.class)) {
            final Table t = c.getAnnotation(Table.class);
            if (t.name() != null && !t.name().isBlank()) {
                return t.name();
            }
        }

        final String table = c.toString().toLowerCase();
        if (table.endsWith("po")) {
            return table.substring(0, table.length() - 2);

        } else if (table.endsWith("entity")) {
            return table.substring(0, table.length() - 6);
        }

        return table;
    }

    /** Get table pk column name */
    public String pk() {
        final Class<?> c = this.getClass();
        if (c.isAnnotationPresent(Table.class)) {
            final Table t = c.getAnnotation(Table.class);
            if (t.pk() != null && !t.pk().isBlank()) {
                return t.pk();
            }
        }

        Field[] fields = c.getFields();
        for (Field field : fields) {
            final String column = field.getName();
            if ("id".equals(column.toLowerCase())) {
                return column;
            }
        }

        if (fields.length > 0) {
            return fields[0].getName();
        }

        return "";
    }

}