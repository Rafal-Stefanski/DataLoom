package org.example.dataloom.repository;

import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArchivedFruitHarvestEventsRepository extends MongoRepository<FruitHarvestEventEntity, String> {

    Optional<FruitHarvestEventEntity> findByBatchId(String batchId);
}
