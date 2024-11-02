package org.example.dataloom.messaging.fixtures;

import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.example.dataloom.repository.entity.DataLoomCatalogueEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataLoomCatalogueEntityFixture {

    public static DataLoomCatalogueEntity dataLoomCatalogueEntity = DataLoomCatalogueEntity.builder()
            .batchId(new ArrayList<>(List.of("APPLE-BATCH-001")))
            .farmLocation(new ArrayList<>(List.of("farm A")))
            .fruitType(FruitType.Apple)
            .harvestDate(new ArrayList<>(List.of("2024-10-01")))
            .location(new HashMap<>(Map.of("APPLE-BATCH-001" ,"warehouse A")))
            .availableKg(100)
            .status("IN_STOCK")
            .quality(QualityGrade.A_PLUS)
            .inspectorId("inspector-001")
            .inspectionDate("2024-10-01")
            .remarks("Good quality apples")
            .build();
}
