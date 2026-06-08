package jamesph.TaskManager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jamesph.TaskManager.controller.param.TaskParam;
import jamesph.TaskManager.controller.request.CreateTaskRequest;
import jamesph.TaskManager.controller.request.UpdateTaskRequest;
import jamesph.TaskManager.dto.PaginatedResponse;
import jamesph.TaskManager.dto.TaskDTO;
import jamesph.TaskManager.exception.TaskException;
import jamesph.TaskManager.exception.UserException;
import jamesph.TaskManager.mapper.TaskMapper;
import jamesph.TaskManager.model.Task;
import jamesph.TaskManager.model.Users;
import jamesph.TaskManager.repository.TaskRepository;
import jamesph.TaskManager.repository.UserRepository;
import jamesph.TaskManager.service.TaskService;
import jamesph.TaskManager.util.UtilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;

  private TaskDTO mapEntity(Task task) {
    return TaskMapper.INSTANCE.entityDto(task);
  }

  private List<TaskDTO> mapEntities(List<Task> entities) {
    final String signature = "TaskServiceImpl::mapEntities";
    String msg;
    List<TaskDTO> dtos = new ArrayList<>();

    if (!entities.isEmpty()) {
      try {
        msg = String.format("Mapping total of %d entities", entities.size());
        UtilityService.logInfo(signature, msg);

        List<TaskDTO> convertedEntities = entities.stream().map(this::mapEntity).toList();
        dtos.addAll(convertedEntities);

        msg = String.format("Successfully mapped total of %d entities", convertedEntities.size());
        UtilityService.logInfo(signature, msg);

      } catch (Exception ex) {
        msg = "Unexpected ERROR on mapping entities.";
        UtilityService.logError(signature, msg, ex);
        throw TaskException.internal(msg, ex);
      }
    }

    return dtos;
  }

  public PaginatedResponse<TaskDTO> getAllTask(TaskParam param, Pageable pageable) {
    final String signature = "TaskServiceImpl::getAllTask";
    String msg;
    msg = "Attempting to get all Task...";
    UtilityService.logInfo(signature, msg);

    try {
      Page<Task> pagedEntities = taskRepository.findAllTask(param, pageable);
      if (pagedEntities.isEmpty()) {
        return new PaginatedResponse<TaskDTO>();
      }
      msg = String.format("Successfully fetched %d Task", pagedEntities.getNumberOfElements());
      UtilityService.logInfo(signature, msg);

      List<TaskDTO> content = this.mapEntities(pagedEntities.getContent());
      return new PaginatedResponse<>(content, pagedEntities.getNumber(), pagedEntities.getSize(),
          pagedEntities.getTotalElements(), pagedEntities.getTotalPages());

    } catch (Exception ex) {
      msg = "Unexpected ERROR on getting all Task.";
      UtilityService.logError(signature, msg, ex);
      throw TaskException.internal(msg, ex);
    }
  }

  public List<Task> findTasksByUuids(Set<UUID> uuids) {
    final String signature = "TaskServiceImpl::findTasksByUuids";
    String msg;
    msg = "Attempting to find TASKS by uuids...";
    UtilityService.logInfo(signature, msg);

    try {
      List<Task> tasks = taskRepository.findTasksByUuids(uuids);
      msg = String.format("Successfully fetched %d Task", tasks.size());
      UtilityService.logInfo(signature, msg);
      return tasks;

    } catch (Exception ex) {
      msg = "Unexpected ERROR on finding TASKS by uuids.";
      UtilityService.logError(signature, msg, ex);
      throw TaskException.internal(msg, ex);
    }
  }

  public List<TaskDTO> getByOwnerUuid(UUID ownerUuid) {
    final String signature = "TaskServiceImpl::getByOwnerUuid";
    List<TaskDTO> dtos = new ArrayList<>();
    String msg = String.format("Attempting to get task for USER with UUID %s", ownerUuid);
    UtilityService.logInfo(signature, msg);
    try {
      List<Task> tasks = taskRepository.findByOwnerUuid(ownerUuid);
      msg = String.format("Successfully fetched %d Task for USER with UUID %s", tasks.size(), ownerUuid);
      UtilityService.logInfo(signature, msg);
      dtos = this.mapEntities(tasks);
    } catch (Exception ex) {
      msg = "Unexpected ERROR on getting all Task.";
      UtilityService.logError(signature, msg, ex);
      throw TaskException.internal(msg, ex);
    }
    return dtos;
  }

  public TaskDTO addTask(CreateTaskRequest request) {
    final String signature = "TaskServiceImpl::addTask";
    String msg;
    String taskTitle = request.getTitle();
    msg = String.format("Attempting to save Task with title %s ", taskTitle);
    UtilityService.logInfo(signature, msg);

    Task task = TaskMapper.INSTANCE.requestToEntity(request);
    UUID ownerUuid = request.getOwnerUuid();

    try {
      if (ownerUuid != null) {
        Users user = this.userRepository.findByUuid(ownerUuid);
        if (user == null) {
          throw UserException.notFound(ownerUuid);
        }
        task.setOwner(user);
      }

      Task savedEntity = this.taskRepository.save(task);
      msg = String.format("Successfully saved Task %s ", taskTitle);
      UtilityService.logInfo(signature, msg);

      return this.mapEntity(savedEntity);

    } catch (UserException ex) {
      msg = String.format("User with UUID %s does not exist.", ownerUuid);
      UtilityService.logError(signature, msg, ex);
      throw TaskException.conflict(msg);
    } catch (Exception ex) {
      msg = String.format("Unexpected ERROR on saving Task %s", taskTitle);
      UtilityService.logError(signature, msg, ex);
      throw TaskException.internal(signature, ex);
    }
  }

  public TaskDTO updateTask(UUID taskUuid, UpdateTaskRequest request) {
    final String signature = "TaskServiceImpl::updateTask";
    String msg;
    UUID ownerUuid = request.getOwnerUuid();

    msg = String.format("Attempting to update Task with UUID %s", taskUuid);
    UtilityService.logInfo(signature, msg);

    try {
      Task task = this.taskRepository.findByUuid(taskUuid);
      if (task == null) {
        throw TaskException.notFound(taskUuid);
      }

      if (ownerUuid != null) {
        UtilityService.logInfo(signature, "Owner EXISTS");
        Users owner = this.userRepository.findByUuid(ownerUuid);
        if (owner == null) {
          throw UserException.notFound(ownerUuid);
        }
        request.setOwner(owner);
      }
      TaskMapper.INSTANCE.updateTaskFroMRequest(request, task);
      Task updatedEntity = this.taskRepository.save(task);

      msg = String.format("Successfully updated Task with UUID %s", taskUuid);
      UtilityService.logInfo(signature, msg);

      return this.mapEntity(updatedEntity);

    } catch (UserException ex) {
      msg = String.format("User with UUID %s does not exist.", ownerUuid);
      UtilityService.logError(signature, msg, ex);
      throw TaskException.conflict(msg);
    } catch (TaskException ex) {
      msg = String.format("Task with UUID %s does not exist. ", taskUuid);
      UtilityService.logError(signature, msg, ex);
      throw TaskException.notFound(taskUuid);
    } catch (Exception ex) {
      msg = String.format("Unexpected Error on updating Task with UUID %s", taskUuid);
      UtilityService.logError(signature, msg, ex);
      throw TaskException.internal(msg, ex);
    }
  }

  /**
   * Deletes a task by its UUID.
   *
   * @param taskUuid the UUID of the task to delete
   * @throws TaskException if the task does not exist or an error occurs during
   *                       deletion
   */
  public void deleteTask(UUID taskUuid) {
    String signature = "TaskServiceImpl::deleteTask";
    String msg;
    try {
      msg = String.format("Attempting to delete TASK with UUID %s", taskUuid);
      UtilityService.logInfo(signature, msg);

      Task task = this.taskRepository.findByUuid(taskUuid);
      if (task == null) {
        throw TaskException.notFound(taskUuid);
      }

      this.taskRepository.delete(task);

      msg = String.format("Successfully deleted TASK with UUID %s", taskUuid);
      UtilityService.logInfo(signature, msg);

    } catch (TaskException ex) {
      msg = String.format("Task with UUID %s does not exist. ", taskUuid);
      UtilityService.logError(signature, msg, ex);
      throw TaskException.notFound(taskUuid);
    } catch (Exception ex) {
      msg = String.format("Unexpected Error on deleting Task with UUID %s", taskUuid);
      UtilityService.logError(signature, msg, ex);
      throw TaskException.internal(msg, ex);
    }
  }

  public void deleteTasks(Set<UUID> taskUuids) {
    String signature = "TaskServiceImpl::deleteTask";
    String msg;
    try {
      msg = String.format("Attempting to delete %d TASKS", taskUuids.size());
      UtilityService.logInfo(signature, msg);

      List<Task> tasks = this.taskRepository.findTasksByUuids(taskUuids);
      if (tasks.isEmpty()) {
        msg = String.format("%d Task does not EXISTS.", taskUuids.size());
        throw TaskException.conflict(msg);
      }

      this.taskRepository.deleteAll(tasks);

      msg = String.format("Successfully deleted %d TASK.", tasks.size());
      UtilityService.logInfo(signature, msg);

    } catch (TaskException ex) {
      msg = String.format("%d Task does not EXISTS.", taskUuids.size());
      UtilityService.logError(signature, msg, ex);
      throw TaskException.conflict(msg);
    } catch (Exception ex) {
      msg = String.format("Unexpected Error on deleting %d Tasks", taskUuids.size());
      UtilityService.logError(signature, msg, ex);
      throw TaskException.internal(msg, ex);
    }
  }
}
