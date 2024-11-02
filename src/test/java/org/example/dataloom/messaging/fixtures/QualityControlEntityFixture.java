package org.example.dataloom.messaging.fixtures;

import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.example.dataloom.repository.entity.QualityControlEntity;

public class QualityControlEntityFixture {
    public static QualityControlEntity qualityControlEntity = QualityControlEntity.builder()
            .batchId("APPLE-BATCH-001")
            .quality(QualityGrade.A_PLUS)
            .inspectorId("inspector-001")
            .inspectionDate("2024-10-01")
            .remarks("Good quality apples")
            .fruitType(FruitType.Apple)
            .build();
}
