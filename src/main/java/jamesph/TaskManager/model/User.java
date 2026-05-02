package jamesph.TaskManager.model;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jamesph.TaskManager.model.common.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`user`")
@Getter
@Setter
public class User extends BaseModel {

  @Id
  @Column(name = "`uuid`", insertable = true, updatable = false, nullable = false)
  private UUID uuid;

  @Column(name = "first_name", length = 50, nullable = false)
  private String firstName;

  @Column(name = "middle_name", length = 50, nullable = false)
  private String middleName;

  @Column(name = "last_name", length = 50, nullable = false)
  private String lastName;

  @Column(name = "email", length = 30, nullable = false)
  private String email;

  @Column(name = "phone_number")
  private Integer phoneNumber;

}
