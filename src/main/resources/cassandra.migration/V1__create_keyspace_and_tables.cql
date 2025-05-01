CREATE KEYSPACE IF NOT EXISTS my_keyspace
    WITH replication = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
        }
     AND durable_writes = true;

CREATE TABLE IF NOT EXISTS my_keyspace.user_audit
(
    id            UUID,
    event_time    TIMESTAMP,
    event_type    TEXT,
    event_details TEXT,
    PRIMARY KEY ((id), event_time)
) WITH default_time_to_live = 31536000

create table if not exists outbox
(
    id   UUID PRIMARY KEY,
    data TEXT
);