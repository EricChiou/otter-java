package ws.otter.model.user;

import org.springframework.stereotype.Component;

@Component
public class UserPo {

    // table name
    public final String table = "user";

    // pk
    public final String pk = "id";

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