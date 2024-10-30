package org.example.dataloom.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.example.dataloom.repository.ArchivedFruitHarvestEventsRepository;
import org.example.dataloom.repository.ArchivedInventoryUpdateEventsRepository;
import org.example.dataloom.repository.ArchivedQualityControlEventsRepository;
import org.example.dataloom.repository.DataLoomRepository;
import org.example.dataloom.repository.entity.DataLoomCatalogueEntity;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.example.dataloom.repository.entity.InventoryUpdateEntity;
import org.example.dataloom.repository.entity.QualityControlEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataLoomService {

    private final DataLoomRepository dataLoomRepository;
    private final ArchivedQualityControlEventsRepository archivedQualityControlEventsRepository;
    private final ArchivedFruitHarvestEventsRepository archivedFruitHarvestEventsRepository;
    private final ArchivedInventoryUpdateEventsRepository archivedInventoryUpdateEventsRepository;

    @Scheduled(fixedDelayString = "${schedule.dataloom.catalogue.update.interval}")
    private void runDataLoomCatalogueUpdate() {
        log.info("Started DataLoomCatalogue update process.");
        List<FruitHarvestEventEntity> fruitHarvestEvents = archivedFruitHarvestEventsRepository.findAll();
        List<QualityControlEntity> qualityControlEvents = archivedQualityControlEventsRepository.findAll();
        List<InventoryUpdateEntity> inventoryUpdateEvents = archivedInventoryUpdateEventsRepository.findAll();
        for (FruitHarvestEventEntity fruitHarvestEvent : fruitHarvestEvents) {
            String batchId = fruitHarvestEvent.getBatchId();

            Optional<QualityControlEntity> qualityControlEntity = qualityControlEvents.stream()
                    .filter(qualityControl -> qualityControl.getBatchId().equals(batchId))
                    .findFirst();

            Optional<InventoryUpdateEntity> inventoryUpdateEntity = inventoryUpdateEvents.stream()
                    .filter(inventoryUpdate -> inventoryUpdate.getBatchId().equals(batchId))
                    .findFirst();

            if (qualityControlEntity.isPresent() && inventoryUpdateEntity.isPresent()) {
                saveFruitHarvestEventToFruitLoom(fruitHarvestEvent, qualityControlEntity.get(), inventoryUpdateEntity.get());
            }

        }
        log.info("Finished DataLoomCatalogue update process.");
    }

    private void saveFruitHarvestEventToFruitLoom(FruitHarvestEventEntity fruitHarvestEvent,
                                                  QualityControlEntity qualityControlEntity,
                                                  InventoryUpdateEntity inventoryUpdateEntity) {
        // check if FruitType and QualityGrade exists in DataLoomCatalogue
        Optional<DataLoomCatalogueEntity> dataLoomCatalogueEntity = dataLoomRepository.findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(
                fruitHarvestEvent.getFruitType(),
                qualityControlEntity.getQuality());
        // if so update the document with the new information
        if (dataLoomCatalogueEntity.isPresent()) {
            updateFruitHarvestEventToDataLoomCollection(
                    fruitHarvestEvent,
                    qualityControlEntity,
                    inventoryUpdateEntity,
                    dataLoomCatalogueEntity.get());
            log.info("Updated document in DataLoomCatalogue for: {}, {}, added {} kg, to: {}", fruitHarvestEvent.getFruitType(), qualityControlEntity.getQuality(), fruitHarvestEvent.getQuantityKg(), inventoryUpdateEntity.getLocation());
        } else {
            // or create a new document
            var newDataLoomCatalogueEntity = createNewDocument(fruitHarvestEvent, qualityControlEntity, inventoryUpdateEntity);
            dataLoomRepository.save(newDataLoomCatalogueEntity);
            log.info("Created new document in DataLoomCatalogue for: {}, {}", fruitHarvestEvent.getFruitType(), qualityControlEntity.getQuality());
        }
        deleteArchivedDocuments(fruitHarvestEvent, qualityControlEntity, inventoryUpdateEntity);
    }

    private void deleteArchivedDocuments(FruitHarvestEventEntity fruitHarvestEvent,
                                         QualityControlEntity qualityControlEntity,
                                         InventoryUpdateEntity inventoryUpdateEntity) {
        archivedFruitHarvestEventsRepository.delete(fruitHarvestEvent);
        archivedQualityControlEventsRepository.delete(qualityControlEntity);
        archivedInventoryUpdateEventsRepository.delete(inventoryUpdateEntity);
        log.info("Deleted archived documents for batchId: {}", fruitHarvestEvent.getBatchId());
    }

    private DataLoomCatalogueEntity createNewDocument(FruitHarvestEventEntity fruitHarvestEvent,
                                                      QualityControlEntity qualityControlEntity,
                                                      InventoryUpdateEntity inventoryUpdateEntity) {
        return DataLoomCatalogueEntity.builder()
                .batchId(List.of(fruitHarvestEvent.getBatchId()))
                .farmLocation(List.of(fruitHarvestEvent.getFarmLocation()))
                .fruitType(fruitHarvestEvent.getFruitType())
                .harvestDate(List.of(fruitHarvestEvent.getHarvestDate()))
                .location(Map.of(inventoryUpdateEntity.getBatchId(), inventoryUpdateEntity.getLocation()))
                .availableKg(fruitHarvestEvent.getQuantityKg())
                .status(inventoryUpdateEntity.getStatus())
                .quality(qualityControlEntity.getQuality())
                .inspectorId(qualityControlEntity.getInspectorId())
                .inspectionDate(qualityControlEntity.getInspectionDate())
                .remarks(qualityControlEntity.getRemarks())
                .build();
    }

    private void updateFruitHarvestEventToDataLoomCollection(FruitHarvestEventEntity fruitHarvestEvent,
                                                             QualityControlEntity qualityControlEntity,
                                                             InventoryUpdateEntity inventoryUpdateEntity,
                                                             DataLoomCatalogueEntity dataLoomCatalogueEntity) {
        // update the existing document with the new information
        log.info("Updating {}, {} in DataLoomCatalogue.", fruitHarvestEvent.getFruitType(), qualityControlEntity.getQuality());

        dataLoomCatalogueEntity.getBatchId().add(fruitHarvestEvent.getBatchId());
        dataLoomCatalogueEntity.getFarmLocation().add(fruitHarvestEvent.getFarmLocation());
        dataLoomCatalogueEntity.getHarvestDate().add(fruitHarvestEvent.getHarvestDate());
        dataLoomCatalogueEntity.setAvailableKg(dataLoomCatalogueEntity.getAvailableKg() + fruitHarvestEvent.getQuantityKg());
        dataLoomCatalogueEntity.getLocation().put(inventoryUpdateEntity.getBatchId(), inventoryUpdateEntity.getLocation());
        dataLoomCatalogueEntity.setStatus(inventoryUpdateEntity.getStatus());
        dataLoomCatalogueEntity.setInspectorId(qualityControlEntity.getInspectorId());
        dataLoomCatalogueEntity.setInspectionDate(qualityControlEntity.getInspectionDate());
        dataLoomCatalogueEntity.setRemarks(qualityControlEntity.getRemarks());

        dataLoomRepository.save(dataLoomCatalogueEntity);
    }


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
//                updateFruitHarvestEventToDataLoomCollection(fruitHarvestEvent, qualityControlEntity.get(), dataLoomCatalogueEntity.get());
            } else {
//                var newDataLoomCatalogueEntity = createNewDocument(fruitHarvestEvent, qualityControlEntity.get());
//                dataLoomRepository.save(newDataLoomCatalogueEntity);
            }
        } else {
            log.warn("Quality check does not exist for batchId: {}, batch saved to archived collection", fruitHarvestEvent.getBatchId());
            archivedFruitHarvestEventsRepository.save(fruitHarvestEvent);
        }
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
