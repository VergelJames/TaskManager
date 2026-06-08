package jamesph.TaskManager.repository;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jamesph.TaskManager.controller.param.UserParam;
import jamesph.TaskManager.model.Users;

public interface UserRepository extends JpaRepository<Users, UUID> {

  @Query("""
          SELECT u FROM Users u
          WHERE u.uuid = :uuid
      """)
  Users findByUuid(@Param("uuid") UUID uuid);

  @Query("""
      SELECT u FROM Users u
      WHERE (:#{#param.keyword} IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :#{#param.keyword}, '%')))
      OR (:#{#param.keyword} IS NULL OR LOWER(u.middleName) LIKE LOWER(CONCAT('%', :#{#param.keyword}, '%')))
      OR (:#{#param.keyword} IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :#{#param.keyword}, '%')))
      OR (:#{#param.keyword} IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :#{#param.keyword}, '%')))
      """)
  Page<Users> findAllUsers(@Param("param") UserParam param, Pageable pageable);

}
