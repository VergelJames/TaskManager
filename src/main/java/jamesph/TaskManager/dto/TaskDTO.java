package jamesph.TaskManager.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskDTO {
  private UUID uuid;
  private String title;
  private String description;
  private LocalDate dueOnDate;
  private String status;
  private String priority;
}
