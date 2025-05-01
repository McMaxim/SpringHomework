package app.repository;

import app.model.Outbox;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutboxRepository extends CassandraRepository<Outbox, UUID> {
}
