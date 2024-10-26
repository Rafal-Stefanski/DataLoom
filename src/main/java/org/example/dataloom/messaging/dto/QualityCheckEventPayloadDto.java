package org.example.dataloom.messaging.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("payload")
public class QualityCheckEventPayloadDto {
    private String eventId; // extracted from metaData in Kafka message
    private String batchId;
    private FruitType fruitType;
    private QualityGrade quality;
    private String inspectorId;
    private String inspectionDate;
    private String remarks;
}
