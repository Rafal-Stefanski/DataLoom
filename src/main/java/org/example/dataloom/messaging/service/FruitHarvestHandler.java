package org.example.dataloom.messaging.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.example.dataloom.repository.ArchivedFruitHarvestEventsRepository;
import org.example.dataloom.repository.ArchivedQualityControlEventsRepository;
import org.example.dataloom.repository.entity.QualityControlEntity;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.example.dataloom.repository.mapper.FruitHarvestMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FruitHarvestHandler {

    private final DataLoomService dataLoomService;
    private final ArchivedFruitHarvestEventsRepository archivedFruitHarvestRepository;
    private final ArchivedQualityControlEventsRepository archivedQualityControlRepository;

    private final FruitHarvestMapper fruitHarvestMapperImpl;

    public void handle(FruitHarvestEventPayloadDto fruitHarvestEventPayloadDto) {
        // get harvest event
        FruitHarvestEventEntity entity = fruitHarvestMapperImpl.toEntity(fruitHarvestEventPayloadDto);
        ObjectMapper objectMapper = new ObjectMapper();
        // check for quality control event
        Optional<QualityControlEntity> qualityControlByBatchId = archivedQualityControlRepository.findFirstByBatchId(entity.getBatchId());
        qualityControlByBatchId
                .ifPresentOrElse(
                        archivedFruitHarvestEventEntity -> {
                            log.info("Quality control event exists for batchId: {}", entity.getBatchId());
                            // check if quality control passed
                            String grade = qualityControlByBatchId.get().getQuality().getGrade();


                            if (!Objects.equals(grade, QualityGrade.D.getGrade()) &&
                                    !Objects.equals(grade, QualityGrade.C.getGrade())) {
                                log.info("Quality control passed for batchId: {}", entity.getBatchId());
                                // update inventory

                                // todo: update inventory: check for existing in inventory fruit with the same grade and add quantity.
                                

                            } else {
                                // send notification to location of origin
                                log.info("Quality control failed for batchId: {}", entity.getBatchId());
                            }


                            archivedQualityControlRepository.save(archivedFruitHarvestEventEntity);
                        },
                        () -> log.info("Quality control event doesn't exist for batchId: {}", entity.getBatchId())
                );



        archivedFruitHarvestRepository.save(entity);
        log.info("Received and saved to database Fruit Harvest Event: {}", fruitHarvestEventPayloadDto);

        log.info("Handling fruit harvest event");

        // todo:
        // 1. Check if quality control was done
        // 2. if not done, save to archived_fruit_harvest_events
        // 3. If quality control was done and is passed update inventory
        // 4. If quality control was done and is failed, send notification to location of origin
    }
}
