package ws.otter.model.role;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.stereotype.Component;

@Component
public class RoleEntity {

    @JsonInclude(Include.NON_NULL)
    public Integer id;

    @JsonInclude(Include.NON_NULL)
    public String code;

    @JsonInclude(Include.NON_NULL)
    public String name;

    @JsonInclude(Include.NON_NULL)
    public Integer lv;

    @JsonInclude(Include.NON_NULL)
    public Integer sortNo;

    @JsonInclude(Include.NON_NULL)
    public Boolean enable;

    @JsonInclude(Include.NON_NULL)
    public Date createdDate;

    @JsonInclude(Include.NON_NULL)
    public Date updatedDate;

}