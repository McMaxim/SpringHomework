package app.dto;

import app.model.Action;
import app.model.UserAuditKey;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserAuditDto {

    private UserAuditKey key;
    private Action eventType;
    private String eventDetails;


}