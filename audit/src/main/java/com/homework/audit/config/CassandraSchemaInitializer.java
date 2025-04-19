package com.homework.audit.config;

import com.datastax.oss.driver.api.core.CqlSession;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;

@Configuration
public class CassandraSchemaInitializer {

    private final CassandraTemplate cassandraTemplate;
    private final CqlSession cqlSession;

    @Autowired
    public CassandraSchemaInitializer(CassandraTemplate cassandraTemplate, CqlSession cqlSession) {
        this.cassandraTemplate = cassandraTemplate;
        this.cqlSession = cqlSession;
    }

    @PostConstruct
    public void initializeSchema() {
        String createTableWithTTL = "CREATE TABLE IF NOT EXISTS audit_keyspace.user_audit (" +
                "user_id UUID, " +
                "event_time TIMESTAMP, " +
                "event_type TEXT, " +
                "event_details TEXT, " +
                "PRIMARY KEY ((user_id), event_time)" +
                ") WITH CLUSTERING ORDER BY (event_time DESC) " +
                "AND default_time_to_live = 31536000";
        
        cqlSession.execute(createTableWithTTL);
    }
} 