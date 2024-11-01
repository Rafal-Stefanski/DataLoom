package org.example.dataloom.messaging.fixtures;

import lombok.experimental.UtilityClass;
import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.example.dataloom.messaging.dto.FruitType;

@UtilityClass
public class FruitHarvestEventPayloadDtoFixture {

    public static FruitHarvestEventPayloadDto fruitHarvestEventPayloadDto = FruitHarvestEventPayloadDto.builder()
            .batchId("APPLE-BATCH-001")
            .fruitType(FruitType.Apple)
            .farmLocation("farm A")
            .harvestDate("2024-10-01")
            .quantityKg(100)
            .build();
}
