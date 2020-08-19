package ws.otter.model.role;

import org.springframework.stereotype.Component;

import ws.otter.annotation.Table;
import ws.otter.model.BasePo;

@Component
@Table(name = "role", pk = "id")
public class RolePo extends BasePo {

    // column name
    public final String id = "id";

    public final String code = "code";

    public final String name = "name";

    public final String lv = "lv";

    public final String sort_no = "sort_no";

    public final String enable = "enable";

    public final String createdDate = "created_date";

    public final String updatedDate = "updated_date";

}