package org.example.dataloom.messaging.fixtures;

import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;
import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;

@UtilityClass
public class FruitHarvestEventEntityFixture {

    public static FruitHarvestEventEntity fruitHarvestEventEntity = FruitHarvestEventEntity.builder()
            .id(new ObjectId("616f6f6f6f6f6f6f6f6f6f6f"))
            .batchId("APPLE-BATCH-001")
            .fruitType(FruitType.Apple)
            .farmLocation("farm A")
            .harvestDate("2024-10-01")
            .quantityKg(100)
            .build();
}
