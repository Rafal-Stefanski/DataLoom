package org.example.dataloom.repository;

import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.example.dataloom.repository.entity.DataLoomCatalogueEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataLoomRepository extends MongoRepository<DataLoomCatalogueEntity, String> {

    default Optional<DataLoomCatalogueEntity> findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(
            FruitType fruitType, QualityGrade qualityGrade) {
        return findAll().stream()
                .filter(dataLoomCatalogueEntity -> dataLoomCatalogueEntity.getFruitType().equals(fruitType) &&
                        dataLoomCatalogueEntity.getQuality().equals(qualityGrade))
                .findFirst();
    }
}
