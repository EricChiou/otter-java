package ws.otter.model.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonInclude(Include.NON_NULL)
    public Integer id;

    @Column(name = "acc")
    @JsonInclude(Include.NON_NULL)
    public String acc;

    @Column(name = "pwd")
    @JsonInclude(Include.NON_NULL)
    public String pwd;

    @Column(name = "name")
    @JsonInclude(Include.NON_NULL)
    public String name;

    @Column(name = "role_code")
    @JsonInclude(Include.NON_NULL)
    public String roleCode;

    @Column(name = "status")
    @JsonInclude(Include.NON_NULL)
    public String status;

    @Column(name = "created_date")
    @JsonInclude(Include.NON_NULL)
    public Date createdDate;

    @Column(name = "updated_date")
    @JsonInclude(Include.NON_NULL)
    public Date updatedDate;

}