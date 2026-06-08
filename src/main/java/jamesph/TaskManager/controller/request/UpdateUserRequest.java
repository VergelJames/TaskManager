package jamesph.TaskManager.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequest {
  private String firstName;
  private String middleName;
  private String lastName;
  private String email;
  private Integer phoneNumber;
}
