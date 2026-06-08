package jamesph.TaskManager.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
  private UUID uuid;
  private String firstName;
  private String middleName;
  private String lastName;
  private String email;
  private Integer phoneNumber;
}
