package org.example.dataloom.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "archived_quality_check_events")
public class QualityControlEntity {
    private String eventId;
    private String batchId;
    private FruitType fruitType;
    private QualityGrade quality;
    private String inspectorId;
    private String inspectionDate;
    private String remarks;
}
