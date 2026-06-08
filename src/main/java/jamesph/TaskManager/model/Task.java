package jamesph.TaskManager.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;

  @Column(name = "title", length = 50, nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "due_on_date")
  private LocalDate dueOnDate;

  @Column(name = "status", length = 3, nullable = false)
  private String status;

  @Column(name = "priority", length = 2, nullable = false)
  private String priority;

  // @Column(name = "owner_uuid", nullable = false)
  // private UUID ownerUuid;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_uuid")
  private Users owner;

}
