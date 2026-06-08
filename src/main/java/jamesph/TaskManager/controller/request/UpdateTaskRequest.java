package jamesph.TaskManager.controller.request;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import jamesph.TaskManager.model.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateTaskRequest {
  private String title;
  private String description;
  private LocalDate dueOnDate;
  private UUID ownerUuid;

  @Size(min = 3, max = 3, message = "Status length must be 3.")
  private String status;
  @Size(min = 2, max = 2, message = "Priority length must be 2.")
  private String priority;

  // Populated at service layer.
  private Users owner;
}
