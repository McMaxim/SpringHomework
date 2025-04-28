package com.homework.audit.repository;

import com.homework.audit.model.UserAudit;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAuditRepository extends CassandraRepository<UserAudit, UUID> {

    List<UserAudit> findByUserId(UUID userId);

    @Query("SELECT * FROM user_audit WHERE user_id = ?0 ORDER BY event_time DESC")
    List<UserAudit> findByUserIdOrderByEventTimeDesc(UUID userId);
} 