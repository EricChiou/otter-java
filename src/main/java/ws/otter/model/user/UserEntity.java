package ws.otter.model.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.stereotype.Component;

@Component
public class UserEntity {

    @JsonInclude(Include.NON_NULL)
    public Integer id;

    @JsonInclude(Include.NON_NULL)
    public String acc;

    @JsonInclude(Include.NON_NULL)
    public String pwd;

    @JsonInclude(Include.NON_NULL)
    public String name;

    @JsonInclude(Include.NON_NULL)
    public String roleCode;

    @JsonInclude(Include.NON_NULL)
    public String status;

    @JsonInclude(Include.NON_NULL)
    public Date createdDate;

    @JsonInclude(Include.NON_NULL)
    public Date updatedDate;

}