package jamesph.TaskManager.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import jamesph.TaskManager.controller.param.TaskParam;
import jamesph.TaskManager.controller.request.CreateTaskRequest;
import jamesph.TaskManager.controller.request.UpdateTaskRequest;
import jamesph.TaskManager.dto.PaginatedResponse;
import jamesph.TaskManager.dto.TaskDTO;
import jamesph.TaskManager.model.Task;

public interface TaskService {

  List<TaskDTO> getByOwnerUuid(UUID uuid);

  List<Task> findTasksByUuids(Set<UUID> uuids);

  PaginatedResponse<TaskDTO> getAllTask(TaskParam param, Pageable pageable);

  TaskDTO addTask(CreateTaskRequest request);

  TaskDTO updateTask(UUID taskUuid, UpdateTaskRequest request);

  void deleteTask(UUID taskUuid);

  void deleteTasks(Set<UUID> taskUuids);

}
