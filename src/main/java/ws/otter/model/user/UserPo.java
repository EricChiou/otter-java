package ws.otter.model.user;

import org.springframework.stereotype.Component;

import ws.otter.annotation.Table;
import ws.otter.model.BasePo;

@Component
@Table(name = "user")
public class UserPo extends BasePo {

    // column name
    public final String id = "id";

    public final String acc = "acc";

    public final String pwd = "pwd";

    public final String name = "name";

    public final String roleCode = "role_code";

    public final String status = "status";

    public final String createdDate = "created_date";

    public final String updatedDate = "updated_date";

}