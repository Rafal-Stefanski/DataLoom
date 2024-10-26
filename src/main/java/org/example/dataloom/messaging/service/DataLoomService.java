package org.example.dataloom.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.example.dataloom.repository.ArchivedFruitHarvestEventsRepository;
import org.example.dataloom.repository.ArchivedQualityControlEventsRepository;
import org.example.dataloom.repository.DataLoomRepository;
import org.example.dataloom.repository.entity.QualityControlEntity;
import org.example.dataloom.repository.entity.DataLoomCatalogueEntity;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataLoomService {

    private final DataLoomRepository dataLoomRepository;
    private final ArchivedQualityControlEventsRepository archivedQualityControlEventsRepository;
    private final ArchivedFruitHarvestEventsRepository archivedFruitHarvestEventsRepository;

    // DataLoomCatalogue stores information about fruit harvest events, inventory updates, and quality checks
    // fruit events are pinned to a batchId
    // quality checks are pinned to a batchId
    // inventory updates are pinned to a location
    // every document in collection contains information tied to FruitType and QualityGrade, other information are added to the document.

    public void saveFruitHarvestEvent(FruitHarvestEventEntity fruitHarvestEvent) {
        // check if quality check exists for this fruitHarvestEvent.batchId
        log.info("Started process of adding FruitHarvestEvent to DataLoomCatalogue: {}", fruitHarvestEvent);
        Optional<QualityControlEntity> qualityControlEntity =
                archivedQualityControlEventsRepository.findFirstByBatchId(fruitHarvestEvent.getBatchId());
        if (qualityControlEntity.isPresent()) {

            log.info("Quality check exists for batchId: {}", fruitHarvestEvent.getBatchId());
            // check if FruitType and QualityGrade exists in DataLoomCatalogue
            Optional<DataLoomCatalogueEntity> dataLoomCatalogueEntity =
                    dataLoomRepository.findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(fruitHarvestEvent.getFruitType(),
                            qualityControlEntity.get().getQuality());
            // if so update the document with the new information
            if (dataLoomCatalogueEntity.isPresent()) {
                updateFruitHarvestEventToDataLoomCollection(fruitHarvestEvent, qualityControlEntity.get(), dataLoomCatalogueEntity.get());
            } else {
                createNewDocument(fruitHarvestEvent, qualityControlEntity.get());
            }
            // if not create a new document.
        } else {
            log.warn("Quality check does not exist for batchId: {}, batch saved to archived collection", fruitHarvestEvent.getBatchId());
            archivedFruitHarvestEventsRepository.save(fruitHarvestEvent);
        }
    }

    private void createNewDocument(FruitHarvestEventEntity fruitHarvestEvent, QualityControlEntity QualityControlEntity) {
    }

    private void updateFruitHarvestEventToDataLoomCollection(FruitHarvestEventEntity fruitHarvestEvent,
                                                             QualityControlEntity qualityControlEntity,
                                                             DataLoomCatalogueEntity dataLoomCatalogueEntity) {
        // update the existing document with the new information
        log.info("Saving FruitHarvestEvent to DataLoomCatalogue: {}", fruitHarvestEvent);


    }


    public boolean isDataLoomCatalogueEntityExists(FruitType fruitType, QualityGrade qualityGrade) {
        return dataLoomRepository.findAll().stream()
                .anyMatch(dataLoomCatalogueEntity -> dataLoomCatalogueEntity.getFruitType().equals(fruitType) &&
                        dataLoomCatalogueEntity.getQuality().equals(qualityGrade));
    }

    public void saveDataLoomCatalogueEntity(DataLoomCatalogueEntity dataLoomCatalogueEntity) {
        log.info("Saving DataLoomCatalogueEntity: {}", dataLoomCatalogueEntity);
        dataLoomRepository.save(dataLoomCatalogueEntity);
    }

}
