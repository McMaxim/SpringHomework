CREATE KEYSPACE IF NOT EXISTS audit_keyspace_test 
WITH REPLICATION = { 
  'class' : 'SimpleStrategy', 
  'replication_factor' : 1 
};

USE audit_keyspace_test;

CREATE TABLE IF NOT EXISTS user_audit (
  user_id UUID,
  event_time TIMESTAMP,
  event_type TEXT,
  event_details TEXT,
  PRIMARY KEY ((user_id), event_time)
) WITH CLUSTERING ORDER BY (event_time DESC)
  AND default_time_to_live = 31536000; 