package org.example.dataloom.messaging.fixtures;

import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.repository.entity.InventoryUpdateEntity;

public class InventoryUpdateEntityFixture {

    public static InventoryUpdateEntity inventoryUpdateEntity = InventoryUpdateEntity.builder()
            .batchId("APPLE-BATCH-001")
            .fruitType(FruitType.Apple)
            .availableKg(100)
            .location("warehouse A")
            .status("IN_STOCK")
            .build();
}
