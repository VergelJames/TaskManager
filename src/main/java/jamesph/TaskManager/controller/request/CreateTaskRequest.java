package jamesph.TaskManager.controller.request;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTaskRequest {

  @NotBlank(message = "Title is required.")
  private String title;
  private String description;
  private LocalDate dueOnDate;
  private UUID ownerUuid;
  

  @Length(min = 3, max = 3, message = "Status length must be 3.")
  private String status;
  @Size(min = 2, max = 2, message = "Priority length must be 2.")
  @NotBlank(message = "Priority is required.")
  private String priority;
}
