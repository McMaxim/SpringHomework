package app.repository;

import app.model.UserAudit;
import app.model.UserAuditKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends CassandraRepository<UserAudit, UserAuditKey> {
}
