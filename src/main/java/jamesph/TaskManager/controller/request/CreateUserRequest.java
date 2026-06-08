package jamesph.TaskManager.controller.request;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jamesph.TaskManager.model.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest {
  @NotBlank(message = "First Name is required.")
  private String firstName;
  private String middleName;
  @NotBlank(message = "Last Name is required.")
  private String lastName;
  @NotBlank(message = "Email is required.")
  private String email;
  private Integer phoneNumber;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<UUID> taskUuids;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<Task> tasks;

}
