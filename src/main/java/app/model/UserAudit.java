package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@AllArgsConstructor
@Table(value = "user_audit")
public class UserAudit {

    @PrimaryKey
    private UserAuditKey key;

    @Column(value = "event_type")
    private Action eventType;

    @Column(value = "event_details")
    private String eventDetails;


}