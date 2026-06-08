package jamesph.TaskManager.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import jamesph.TaskManager.controller.param.UserParam;
import jamesph.TaskManager.controller.request.CreateUserRequest;
import jamesph.TaskManager.controller.request.UpdateUserRequest;
import jamesph.TaskManager.dto.PaginatedResponse;
import jamesph.TaskManager.dto.UserDTO;

public interface UserService {
  UserDTO findByUuid(UUID uuid);
  PaginatedResponse<UserDTO> getAllUsers(UserParam param, Pageable pageable);
  UserDTO createUser(CreateUserRequest request);
  UserDTO updateUser(UUID uuid, UpdateUserRequest request);
  void deleteUser(UUID uuid);
}
