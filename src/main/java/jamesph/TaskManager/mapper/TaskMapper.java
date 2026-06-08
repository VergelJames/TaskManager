package jamesph.TaskManager.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import jamesph.TaskManager.controller.request.CreateTaskRequest;
import jamesph.TaskManager.controller.request.UpdateTaskRequest;
import jamesph.TaskManager.dto.TaskDTO;
import jamesph.TaskManager.model.Task;

@Mapper
public interface TaskMapper {
  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  TaskDTO entityDto(Task entity);

  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Task requestToEntity(CreateTaskRequest request);
  // Task dtoToEntity(TaskDTO dto);

  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTaskFroMRequest(UpdateTaskRequest request, @MappingTarget Task task);
}
