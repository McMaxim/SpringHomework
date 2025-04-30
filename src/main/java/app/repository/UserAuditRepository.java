package app.repository;

import app.model.UserAudit;
import app.model.UserAuditKey;
import org.springframework.data.cassandra.repository.CassandraRepository;


public interface UserAuditRepository extends CassandraRepository<UserAudit, UserAuditKey> {
}
