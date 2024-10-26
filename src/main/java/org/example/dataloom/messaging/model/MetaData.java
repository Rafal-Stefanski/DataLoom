package org.example.dataloom.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaData {
    private String eventId;
    private EventType eventType;
    private String timestamp;
    private String userId;
    private String clientId;
    private String source;
}
