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
public class FruitHarvestEventPayloadDto {
    private String eventId; // extracted from metaData in Kafka message
    private String batchId;
    private String farmLocation;
    private FruitType fruitType;
    private String harvestDate;
    private int quantityKg;
}
