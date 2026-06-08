package jamesph.TaskManager.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jamesph.TaskManager.controller.param.TaskParam;
import jamesph.TaskManager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query("""
                SELECT t FROM Task t
                JOIN owner u
                WHERE u.uuid = :ownerUuid
            """)
    List<Task> findByOwnerUuid(@Param("ownerUuid") UUID ownerUuid);

    @Query("""
            SELECT t FROM Task t
            WHERE t.uuid = :uuid
            """)
    Task findByUuid(@Param("uuid") UUID uuid);

    @Query("""
              SELECT t FROM Task t
              WHERE (:#{#param.keyword} IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :#{#param.keyword}, '%')))
                AND (:#{#param.startDate} IS NULL OR t.dueOnDate >= :#{#param.startDate})
                AND (:#{#param.endDate} IS NULL OR t.dueOnDate <= :#{#param.endDate})
                AND (:#{#param.ownerUuids} IS NULL OR t.owner.uuid IN :#{#param.ownerUuids})
            """)
    Page<Task> findAllTask(@Param("param") TaskParam param, Pageable pageable);

    @Query("""
            SELECT t FROM Task t
            WHERE t.uuid IN :taskUuids
            """)
    List<Task> findTasksByUuids(@Param("taskUuids") Set<UUID> taskUids);
}
