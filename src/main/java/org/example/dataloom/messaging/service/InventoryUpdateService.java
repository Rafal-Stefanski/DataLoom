package org.example.dataloom.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.InventoryUpdateEventPayloadDto;
import org.example.dataloom.repository.ArchivedInventoryUpdateEventsRepository;
import org.example.dataloom.repository.mapper.DataLoomMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryUpdateService {

    private final ArchivedInventoryUpdateEventsRepository archivedInventoryUpdateEventsRepository;
    private final DataLoomMapper dataLoomMapperImpl;

    public void save(InventoryUpdateEventPayloadDto inventoryUpdateEventPayloadDto) {
        var inventoryUpdateEntity = dataLoomMapperImpl.toEntity(inventoryUpdateEventPayloadDto);
        archivedInventoryUpdateEventsRepository.save(inventoryUpdateEntity);
    }
}
