package org.example.dataloom.messaging.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {
    @JsonProperty("metadata")
    private MetaData metaData;
    @JsonProperty("payload")
    private String payload;
}
