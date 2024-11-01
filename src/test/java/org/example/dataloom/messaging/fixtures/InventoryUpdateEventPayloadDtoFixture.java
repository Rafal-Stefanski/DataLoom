package org.example.dataloom.messaging.fixtures;

import lombok.experimental.UtilityClass;
import org.example.dataloom.messaging.dto.InventoryUpdateEventPayloadDto;

@UtilityClass
public class InventoryUpdateEventPayloadDtoFixture {

    public static InventoryUpdateEventPayloadDto inventoryUpdateEventPayloadDto = org.example.dataloom.messaging.dto.InventoryUpdateEventPayloadDto.builder()
            .batchId("APPLE-BATCH-001")
            .fruitType(org.example.dataloom.messaging.dto.FruitType.Apple)
            .location("farm A")
            .availableKg(100)
            .status("Available")
            .build();
}
