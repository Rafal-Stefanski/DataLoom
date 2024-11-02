package org.example.dataloom.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "data-loom-catalogue")
public class DataLoomCatalogueEntity {

    @Id
    private String id;

    // Fields from FruitHarvestEventPayloadDto
    private List<String> batchId;
    private List<String> farmLocation;
    private FruitType fruitType;
    private List<String> harvestDate;
//    private int quantityKg;   // that field will be aded to availableKg

    // Fields from InventoryUpdateEventPayloadDto
    private Map<String, String> location;
    private int availableKg;
    private String status;

    // Fields from QualityCheckEventPayloadDto
    @BsonRepresentation(BsonType.STRING)
    private QualityGrade quality;
    private String inspectorId;
    private String inspectionDate;
    private String remarks;

    public DataLoomCatalogueEntity withFruitTypeAndQuality(FruitType fruitType, QualityGrade quality) {
        return new DataLoomCatalogueEntity(
                id,
//                eventId,
                batchId,
                farmLocation,
                fruitType,
                harvestDate,
//                quantityKg,
                location,
                availableKg,
                status,
                quality,
                inspectorId,
                inspectionDate,
                remarks);
    }
}
