package jamesph.TaskManager.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jamesph.TaskManager.controller.param.TaskParam;
import jamesph.TaskManager.controller.request.CreateTaskRequest;
import jamesph.TaskManager.controller.request.UpdateTaskRequest;
import jamesph.TaskManager.service.TaskService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

  private final TaskService taskService;

  @GetMapping
  public ResponseEntity<Object> getAllTask(@RequestBody TaskParam param, Pageable pageable) {
    return new ResponseEntity<>(this.taskService.getAllTask(param, pageable), HttpStatus.OK);
  }

  @GetMapping("/{taskUuid}")
  public ResponseEntity<Object> getByOwnerUuid(@PathVariable UUID taskUuid) {
    return new ResponseEntity<>(this.taskService.getByOwnerUuid(taskUuid), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> addTask(@Validated @RequestBody CreateTaskRequest request) {
    return new ResponseEntity<>(this.taskService.addTask(request), HttpStatus.CREATED);
  }

  @PatchMapping("/{taskUuid}")
  public ResponseEntity<Object> updateTask(@PathVariable("taskUuid") UUID taskUuid,
      @Validated @RequestBody UpdateTaskRequest request) {
    return new ResponseEntity<>(this.taskService.updateTask(taskUuid, request), HttpStatus.OK);
  }

  @DeleteMapping("/{taskUuid}")
  public ResponseEntity<Object> deleteTask(@PathVariable("taskUuid") UUID taskUuid) {
    this.taskService.deleteTask(taskUuid);
    return ResponseEntity.noContent().build();
  }
}
