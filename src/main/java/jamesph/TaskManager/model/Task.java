package jamesph.TaskManager.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jamesph.TaskManager.model.common.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task extends BaseModel {

  @Id
  @Column(name = "`uuid`", insertable = true, updatable = false, nullable = false)
  private UUID uuid;

  @Column(name = "title", length = 50, nullable = false)
  private String title;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "dueOnDate", nullable = false)
  private LocalDate dueOnDate;

  @Column(name = "ownerUuid", nullable = false)
  private UUID ownerUuid;

}
