package org.example.dataloom.repository;

import org.example.dataloom.repository.entity.QualityControlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArchivedQualityControlEventsRepository extends MongoRepository<QualityControlEntity, String> {

    Optional<QualityControlEntity> findFirstByBatchId(String batchId);

}
