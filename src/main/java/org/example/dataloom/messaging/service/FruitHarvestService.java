package org.example.dataloom.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.example.dataloom.repository.ArchivedFruitHarvestEventsRepository;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.example.dataloom.repository.mapper.DataLoomMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FruitHarvestService {

    private final ArchivedFruitHarvestEventsRepository fruitHarvestRepository;
    private final DataLoomMapper dataLoomMapperImpl;

    public void save(FruitHarvestEventPayloadDto fruitHarvestEventPayloadDto) {
        var fruitHarvestEntity = dataLoomMapperImpl.toEntity(fruitHarvestEventPayloadDto);
        fruitHarvestRepository.save(fruitHarvestEntity);
        log.info("Received and saved to database Fruit Harvest Event, fruit: {}, with batchId: {}", fruitHarvestEntity.getFruitType(), fruitHarvestEntity.getBatchId());
    }

    public void delete(FruitHarvestEventEntity fruitHarvestEventEntity) {
        fruitHarvestRepository.delete(fruitHarvestEventEntity);
        log.info("Deleted Fruit Harvest Event of fruit: {}, with batchId: {}", fruitHarvestEventEntity.getFruitType(), fruitHarvestEventEntity.getBatchId());
    }

}
