package jamesph.TaskManager.controller.param;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskParam {
  private String keyword;
  private LocalDate startDate;
  private LocalDate endDate;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<UUID> ownerUuids;
}
