package jamesph.TaskManager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jamesph.TaskManager.controller.param.UserParam;
import jamesph.TaskManager.controller.request.CreateUserRequest;
import jamesph.TaskManager.controller.request.UpdateUserRequest;
import jamesph.TaskManager.dto.PaginatedResponse;
import jamesph.TaskManager.dto.UserDTO;
import jamesph.TaskManager.exception.UserException;
import jamesph.TaskManager.mapper.UserMapper;
import jamesph.TaskManager.model.Task;
import jamesph.TaskManager.model.Users;
import jamesph.TaskManager.repository.UserRepository;
import jamesph.TaskManager.service.TaskService;
import jamesph.TaskManager.service.UserService;
import jamesph.TaskManager.util.UtilityService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final TaskService taskService;

  private UserDTO mapEntity(Users user) {
    return UserMapper.INSTANCE.entityDto(user);
  }

  private List<UserDTO> mapEntities(List<Users> entities) {
    final String signature = "UserServiceImpl::mapEntities";
    String msg;
    List<UserDTO> dtos = new ArrayList<>();

    if (!entities.isEmpty()) {
      try {
        msg = String.format("Mapping total of %d entities", entities.size());
        UtilityService.logInfo(signature, msg);

        List<UserDTO> convertedEntities = entities.stream().map(this::mapEntity).toList();
        dtos.addAll(convertedEntities);

        msg = String.format("Successfully mapped total of %d entities", convertedEntities.size());
        UtilityService.logInfo(signature, msg);
      } catch (Exception ex) {
        msg = "Unexpected ERROR on mapping entities.";
        UtilityService.logError(signature, msg, ex);
        throw UserException.internal(msg, ex);
      }
    }
    return dtos;
  }

  public UserDTO findByUuid(UUID uuid) {
    final String signature = "UserServiceImpl::findByUuid";
    String msg;

    msg = String.format("Attempting to find USER with UUID %s", uuid);
    UtilityService.logInfo(signature, msg);

    try {
      Users user = this.userRepository.findByUuid(uuid);
      if (user == null) {
        throw UserException.notFound(uuid);
      }
      return this.mapEntity(user);
    } catch (UserException ex) {
      msg = String.format("User with UUID %s cannot be found.", uuid);
      UtilityService.logError(signature, msg);
      throw UserException.notFound(uuid);
    } catch (Exception ex) {
      msg = "Unexpected ERROR on finding user.";
      UtilityService.logError(signature, msg, ex);
      throw UserException.internal(msg, ex);
    }
  }

  public PaginatedResponse<UserDTO> getAllUsers(UserParam param, Pageable pageable) {
    final String signature = "UserServiceImpl::getAllUsers";
    String msg;
    msg = "Attempting to get all Users...";
    UtilityService.logInfo(signature, msg);

    try {
      Page<Users> pagedEntities = userRepository.findAllUsers(param, pageable);
      if (pagedEntities.isEmpty()) {
        return new PaginatedResponse<UserDTO>();
      }
      msg = String.format("Successfully fetched %d Users", pagedEntities.getNumberOfElements());
      UtilityService.logInfo(signature, msg);

      List<UserDTO> content = this.mapEntities(pagedEntities.getContent());
      return new PaginatedResponse<UserDTO>(content, pagedEntities.getNumber(), pagedEntities.getSize(),
          pagedEntities.getTotalElements(), pagedEntities.getTotalPages());
    } catch (Exception ex) {
      msg = "Unexpected ERROR on getting all Users.";
      UtilityService.logError(signature, msg);
      throw UserException.internal(msg, ex);
    }
  }

  public UserDTO createUser(CreateUserRequest request) {
    final String signature = "UserServiceImpl::createUser";
    String msg;
    Set<UUID> taskUuids = request.getTaskUuids();

    msg = String.format("Attempting to create User..");
    UtilityService.logInfo(signature, msg);

    try {
      Users task = UserMapper.INSTANCE.requestToEntity(request);
      this.mapTasks(taskUuids, task);
      Users savedEntity = this.userRepository.save(task);
      msg = String.format("Successfully saved User with UUID %s", savedEntity.getUuid());
      UtilityService.logInfo(signature, msg);

      return this.mapEntity(savedEntity);
    } catch (Exception ex) {
      msg = "Unexpected ERROR on creating User.";
      UtilityService.logError(signature, msg, ex);
      throw UserException.internal(msg, ex);
    }
  }

  private void mapTasks(Set<UUID> taskUuids, Users user) {
    final String signature = "UserServiceImpl::mapTasks";
    final int totalTaskUuids = taskUuids.size();
    List<Task> assignedTasks = new ArrayList<>();
    String msg;
    msg = String.format("Mapping %d Tasks", totalTaskUuids);

    try {
      if (!taskUuids.isEmpty()) {
        List<Task> tasks = this.taskService.findTasksByUuids(taskUuids);
        tasks.forEach(task -> task.setOwner(user));
        assignedTasks.addAll(tasks);
        user.setTasks(tasks);
      }
    } catch (Exception ex) {
      msg = "Unexpected ERROR on mapping Tasks.";
      UtilityService.logError(signature, msg, ex);
      throw UserException.internal(msg, ex);
    }
    msg = String.format("Successfully mapped %d Tasks", assignedTasks.size());
    UtilityService.logInfo(signature, msg);
  }

  public UserDTO updateUser(UUID uuid, UpdateUserRequest request) {
    final String signature = "UserServiceImpl::updateUser";
    String msg;

    msg = String.format("Attempting to update USER with UUID %s", uuid);
    UtilityService.logInfo(signature, msg);

    try {
      Users user = this.userRepository.findByUuid(uuid);
      if (user == null) {
        throw UserException.notFound(uuid);
      }

      UserMapper.INSTANCE.updateUserFromRequest(request, user);
      Users updatedEntity = this.userRepository.save(user);

      msg = String.format("Successfully updated USER with UUID %s", uuid);
      UtilityService.logError(signature, msg);

      return this.mapEntity(updatedEntity);

    } catch (UserException ex) {
      msg = String.format("User with UUID %s does not exist.", uuid);
      UtilityService.logError(signature, msg, ex);
      throw UserException.internal(msg, ex);
    } catch (Exception ex) {
      msg = String.format("Unexpected ERROR on updating User with UUID %s.", uuid);
      UtilityService.logError(signature, msg, ex);
      throw UserException.internal(msg, ex);
    }
  }

  @Transactional
  public void deleteUser(UUID userUuid) {
    final String signature = "UserServiceImpl::deleteUser";
    String msg;

    msg = String.format("Attempting to delete USER with UUID %s", userUuid);
    UtilityService.logInfo(signature, msg);

    try {
      Users user = this.userRepository.findByUuid(userUuid);
      if (user == null) {
        throw UserException.notFound(userUuid);
      }
      List<Task> assignedTasks = user.getTasks();

      if (!assignedTasks.isEmpty()) {
        this.taskService.deleteTasks(user.getTasks().stream().map(Task::getUuid).collect(Collectors.toSet()));
      }
      
      this.userRepository.delete(user);
      msg = String.format("Sucessfully deleted USER with UUID %s", userUuid);
      UtilityService.logInfo(signature, msg);
    } catch (UserException ex) {
      msg = String.format("User with UUID %s does not exist.", userUuid);
      UtilityService.logError(signature, msg, ex);
      throw UserException.notFound(userUuid);
    } catch (Exception ex) {
      msg = String.format("Unexpected ERROR on deleting User with UUID %s.", userUuid);
      UtilityService.logError(signature, msg, ex);
      throw UserException.internal(msg, ex);
    }
  }

}
