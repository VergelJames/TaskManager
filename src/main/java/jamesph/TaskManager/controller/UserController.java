package jamesph.TaskManager.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jamesph.TaskManager.controller.param.UserParam;
import jamesph.TaskManager.controller.request.CreateUserRequest;
import jamesph.TaskManager.controller.request.UpdateUserRequest;
import jamesph.TaskManager.dto.PaginatedResponse;
import jamesph.TaskManager.dto.UserDTO;
import jamesph.TaskManager.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/{uuid}")
  public ResponseEntity<UserDTO> findByUuid(@PathVariable("uuid") UUID uuid) {
    return new ResponseEntity<>(this.userService.findByUuid(uuid), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<PaginatedResponse<UserDTO>> getAllUsers(@RequestBody UserParam param, Pageable pageable) {
    return new ResponseEntity<>(this.userService.getAllUsers(param, pageable), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
    return new ResponseEntity<>(this.userService.createUser(request), HttpStatus.CREATED);
  }

  @PatchMapping("/{userUuid}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable("userUuid") UUID userUuids,
      @RequestBody UpdateUserRequest request) {
    return new ResponseEntity<>(this.userService.updateUser(userUuids, request), HttpStatus.OK);
  }

  @DeleteMapping("/{userUuid}")
  public ResponseEntity<Object> deleteUser(@PathVariable("userUuid") UUID userUuid) {
    this.userService.deleteUser(userUuid);
    return ResponseEntity.noContent().build();
  }
}
