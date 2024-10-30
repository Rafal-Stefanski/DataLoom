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
@Document(collection = "archived_inventory_update_events")
public class InventoryUpdateEntity {

    @MongoId
    private ObjectId id;
    private FruitType fruitType;
    private String batchId;
    private String location;
    private int availableKg;
    private String status;
}
