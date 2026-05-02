package jamesph.TaskManager.model.common;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseModel {
  @CreatedDate
  private Instant createdAt;

  @LastModifiedBy
  private Instant updatedAt;
}
