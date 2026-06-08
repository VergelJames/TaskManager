package jamesph.TaskManager.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jamesph.TaskManager.model.common.BaseModel;
import lombok.Getter;
import lombok.Setter;

//TODO Find a way to change the table name from users to user
@Entity
@Table(name = "users")
@Getter
@Setter
public class Users extends BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;

  @Column(name = "first_name", length = 50, nullable = false)
  private String firstName;

  @Column(name = "middle_name", length = 50)
  private String middleName;

  @Column(name = "last_name", length = 50, nullable = false)
  private String lastName;

  @Column(name = "email", length = 30, nullable = false)
  private String email;

  @Column(name = "phone_number")
  private Integer phoneNumber;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Task> tasks;

}
