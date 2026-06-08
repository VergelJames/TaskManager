package jamesph.TaskManager.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import jamesph.TaskManager.controller.request.CreateUserRequest;
import jamesph.TaskManager.controller.request.UpdateUserRequest;
import jamesph.TaskManager.dto.UserDTO;
import jamesph.TaskManager.model.Users;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDTO entityDto(Users entity);

  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Users requestToEntity(CreateUserRequest request);

  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "tasks", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateUserFromRequest(UpdateUserRequest request, @MappingTarget Users user);

}
