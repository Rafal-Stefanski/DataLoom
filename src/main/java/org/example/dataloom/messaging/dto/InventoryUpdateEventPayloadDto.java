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
public class InventoryUpdateEventPayloadDto {
    private FruitType fruitType;
    private String batchId;
    private String location;
    private int availableKg;
    private String status;
}
