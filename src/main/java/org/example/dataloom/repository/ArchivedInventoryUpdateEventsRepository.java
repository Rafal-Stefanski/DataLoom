package org.example.dataloom.repository;

import org.example.dataloom.repository.entity.InventoryUpdateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArchivedInventoryUpdateEventsRepository extends MongoRepository<InventoryUpdateEntity, String> {

    Optional<InventoryUpdateEntity> findFirstByBatchId(String batchId);
}
