package org.example.dataloom.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.dataloom.messaging.dto.FruitType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "archived_fruit_harvest_events")
public class FruitHarvestEventEntity {

    @MongoId
    private ObjectId id;
    private String eventId;
    private String batchId;
    private String farmLocation;
    private FruitType fruitType;
    private String harvestDate;
    private int quantityKg;
}
