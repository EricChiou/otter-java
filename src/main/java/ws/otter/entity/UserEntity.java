package ws.otter.entity;

import java.util.Date;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

// @Entity
// @Table(name = "user")
public class UserEntity {

  // table name
  public static final String TABLE = "user";

  // pk
  public static final String PK = "id";

  // column name
  public static final String ID = "id";
  public static final String ACC = "acc";
  public static final String PWD = "pwd";
  public static final String NAME = "name";
  public static final String ROLE_CODE = "role_code";
  public static final String STATUS = "status";
  public static final String CREATED_DATE = "created_date";
  public static final String UPDATED_DATE = "updated_date";

  // @Id
  // @GeneratedValue(strategy = GenerationType.AUTO)
  // @Column(name = "id")
  @JsonInclude(Include.NON_NULL)
  public Integer id;

  // @Column(name = "acc")
  @JsonInclude(Include.NON_NULL)
  public String acc;

  // @Column(name = "pwd")
  @JsonInclude(Include.NON_NULL)
  public String pwd;

  // @Column(name = "name")
  @JsonInclude(Include.NON_NULL)
  public String name;

  // @Column(name = "role_code")
  @JsonInclude(Include.NON_NULL)
  public String roleCode;

  // @Column(name = "status")
  @JsonInclude(Include.NON_NULL)
  public String status;

  // @Column(name = "created_date")
  @JsonInclude(Include.NON_NULL)
  public Date createdDate;

  // @Column(name = "updated_date")
  @JsonInclude(Include.NON_NULL)
  public Date updatedDate;

}